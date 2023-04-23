import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MemberManager {

    private static final String MEMBER_FILE = "member.data";

    //input which is comes from CommandLineArgument:-
    public static void main(String[] Operation) {


        if (Operation.length == 0) {
            System.out.println("Please specify Operations--------> ");
            System.out.println("usage : Opeartions are :[ Add, Update, GetAll, getByCourse, getByContactNumber, Remove] \n Add: [ContactNumber Name Course Fee] \n Update: [contactNO Name Course fee] \n GetAll \n getByCourse: [Course name]\n getByContactNumber: [ContactNumber] \n Remove: [ContactNumber]");
            return;
        }

        String operation = Operation[0];
        if (!isValidOperation(operation)) {
            System.out.println("Please specify Valid Operations");
            System.out.println("USAGE : :[ Add, Update, GetAll, getByCourse, getByContactNumber, Remove]");
            return;
        }
        if (operation.equalsIgnoreCase("ADD")) {
            add(Operation); //done
        } else if (operation.equalsIgnoreCase("Update")) {
            update(Operation);
        } else if (operation.equalsIgnoreCase("GetAll")) {
            getAll(Operation); //done
        } else if (operation.equalsIgnoreCase("getByCourse")) {
            getByCourse(Operation); //process
        } else if (operation.equalsIgnoreCase("getByContactNumber")) {
            getByContactNumber(Operation); //done
        } else if (operation.equalsIgnoreCase("Remove")) {
            Remove(Operation); //process

        }


    }


    //Helper Functions:
    private static boolean isValidOperation(String operation) {
        String validOperations[] = {"Add", "Update", "GetAll", "getByCourse", "getByContactNumber", "Remove"};
        int i = 0;
        while (i < validOperations.length) {
            if (validOperations[i].equalsIgnoreCase(operation)) {
                return true;
            }
            i++;
        }
        return false;
    }


    private static boolean isValidCourse(String course) {
        String courses[] = {"C", "C++", "Java", "Python", "React"};
        int i = 0;
        while (i < courses.length) {
            if (courses[i].equalsIgnoreCase(course)) {
                return true;
            }
            i++;
        }
        return false;
    }


//Working functions :

    private static void add(String[] data) {
        if (data.length != 5) {
            System.out.println("please specify Data its not enough ");
            System.out.println("Add: [1.ContactNumber 2.Name 3.Course 4.Fee]");
            return;
        }
        String contactNumber = data[1];
        String name = data[2];
        String course = data[3];
        int fee;
        //check fee are valid or not
        try {
            fee = Integer.parseInt(data[4]);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.getMessage();
            System.out.println("Please Specify Fee as an INTEGER value");
            return;
        }
        //check course are valid or not
        if (!isValidCourse(course)) {
            System.out.println("Course are Not valid please specify correct");
            System.out.println("Add Courses : [C ,C++ ,Java , Python ,React]");
            return;
        }
        //data added code

        try {
            File f = new File(MEMBER_FILE);
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw"); //file open here  internal pointer point first byte of file
            String fContactNumber;
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) //check for start pointer to end
            {
                fContactNumber = randomAccessFile.readLine();
                System.out.println(fContactNumber);
                if (fContactNumber.equalsIgnoreCase(contactNumber)) //first check is available already or not
                {
                    randomAccessFile.close();
                    System.out.println("this Member Exists");
                    return;
                }
                //if not exists then have to add
                randomAccessFile.readLine();
                randomAccessFile.readLine();
                randomAccessFile.readLine();
            }

            randomAccessFile.writeBytes(contactNumber);
            randomAccessFile.writeBytes("\n");// saprator coz readline always read till \n;
            randomAccessFile.writeBytes(name);
            randomAccessFile.writeBytes("\n");
            randomAccessFile.writeBytes(course);
            randomAccessFile.writeBytes("\n");
            randomAccessFile.writeBytes(String.valueOf((fee)));
            randomAccessFile.writeBytes("\n");
            randomAccessFile.close();
            System.out.println("Member Added..");

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }


    private static void update(String[] gg) {

    }

    private static void getAll(String[] data) {
        if (data.length > 1) {
            System.out.println("Please only type getAll if you want Data of member!");
            return;
        }
        try {
            File f = new File(MEMBER_FILE);
            if (f.exists() == false) {
                System.out.println("there is No file here...!");
                return;
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw");
            if (randomAccessFile.length() == 0) {
                randomAccessFile.close();
                System.out.println("no any members!!!!");//if no data available in file
                return;
            }
            String contactNumber = "";
            String name = "";
            String course = "";
            int fee = 0;
            int totalFeeCollect=0;
            int memberCount=0;
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                    contactNumber= randomAccessFile.readLine();
                    name= randomAccessFile.readLine();
                    course = randomAccessFile.readLine();
                    fee = Integer.parseInt(randomAccessFile.readLine());
                System.out.println("contactNumber: "+ contactNumber  + " NAME: "+name+   " COURSE: "+ course+   " FEE: "+ fee);
                 totalFeeCollect+=fee;
                memberCount++;
            }
            System.out.println("TOTAL FEES COLLECT :"+ totalFeeCollect);
            System.out.println("TOTAL MEMBER :"+ memberCount);
            randomAccessFile.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return;
        }
    }

    private static void getByCourse(String[] input) {
        if (input.length != 2) {
            System.out.println("Please type getByCourse with course Name (input invalid)!");
            System.out.println("the courses is: [C ,C++ ,Java , Python ,React]");
            return;
        }
        //  System.out.println(gg[1]); //courseName
        if (!isValidCourse(input[1])) {
            System.out.println("Course are Not valid please specify correct");
            System.out.println("Add Courses : [C ,C++ ,Java , Python ,React]");
            return;
        }
        String Course = input[1];

            File f = new File(MEMBER_FILE);
            if(f.exists()==false)
            {
                System.out.println("there is No file here...!");
                return;
            }
            String fcontactNumber="";
            String fname="";
            String fcourse="";
            int fee=0;
           Boolean found=false;
            try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw");
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                        fcontactNumber=randomAccessFile.readLine();
                        fname = randomAccessFile.readLine();
                        fcourse =randomAccessFile.readLine();
                        fee= Integer.parseInt(randomAccessFile.readLine());

                if (fcourse.equalsIgnoreCase(Course)) {
                    System.out.println(" contactNumber: "+ fcontactNumber);
                    System.out.println(" Name: "+ fname);
                    System.out.println(" Course: "+ fcourse);
                    System.out.println(" Fee: "+ fee);
                    found = true;
                }
            }
            if(found==false)
            {
                randomAccessFile.close();
                System.out.println("Not Registered any student with that Course"+ Course);
            }

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return;
        }


    }


    private static void getByContactNumber(String[] input) {
        if(input.length!=2)
        {
            System.out.println("(input invalid)!");
            System.out.println("Please type getByContactNUmber with contactNumber (91********) ");
            return;
        }
        String contactNumber = input[1];
        File file = new File(MEMBER_FILE);
        if(file.exists()==false)
        {
            System.out.println("file not Found!");
            return;
        }
       String FIleContactNUmber;
        String FIlename = "";
        String Filecourse = "";
        int Filefee = 0;
        Boolean found=false;
        try
        {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            while(randomAccessFile.getFilePointer() < randomAccessFile.length())
            {
                FIleContactNUmber = randomAccessFile.readLine();
                if(FIleContactNUmber.equalsIgnoreCase(contactNumber))
                {
                    FIlename = randomAccessFile.readLine();
                    Filecourse = randomAccessFile.readLine();
                    Filefee = Integer.parseInt(randomAccessFile.readLine());
                    found=true;
                    break;

                }
                randomAccessFile.readLine();
                randomAccessFile.readLine();
                randomAccessFile.readLine();
            }
            if(found==false)
            {
                randomAccessFile.close();
                System.out.println("Invalid NUmber Please Provide Right contactNumber!!!");
                return;
            }
            else
            {
               System.out.println("contactNumber: "+ contactNumber  + " NAME: "+FIlename+   " COURSE: "+ Filecourse+   " FEE: "+ Filefee);
            }


        }catch (IOException ioException)
        {
            System.out.println(ioException.getMessage());
        }


    }


    private static void Remove(String[] gg) {
        if (gg.length != 2) {
            System.out.println("if you wish to delete, Please type Remove with contactNumber (input invalid)!");
            System.out.println("Usage: [remove 00000000000]");
            return;
        }
        //check number is valid or not
        String fNumber = null;
        try {
            File f = new File(MEMBER_FILE);
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw");
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                fNumber = randomAccessFile.readLine();
                if (fNumber.equalsIgnoreCase(gg[1])) {
//                 randomAccessFile
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }


}
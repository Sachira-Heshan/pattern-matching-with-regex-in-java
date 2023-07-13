// I certify the code of this lab is entirely my own work.

package mainpackage;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterDetails {

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader("D:\\University\\Semester 06\\Software Engineering [EC6060]\\Labs\\Lab 04\\L5\\EmailFiles\\" + fileName));
            //above file path should be change according to the email text file path
        }catch(FileNotFoundException e){
            System.out.println("File Not Found!");
        }
        String email = "";
        String line = null;
        line = br.readLine();
        while(line != null){
            email = email + "\n" + line;
            line = br.readLine();
        }
        br.close();
        return email;
    }

    public static void writeFile(String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\University\\Semester 06\\Software Engineering [EC6060]\\Labs\\Lab 04\\L5\\EmailFiles\\ExtractedData.txt", true));
        //above file path should be change according to the email text file path that data saved into
        bw.write(data);
        bw.close();
    }

    public static String filterDetails(String email){

        String[] details = new String[9];

        String singleMailDetails = null;

        //Define an array with patterns for all details
        String[] patterns = new String[9];
        patterns[0] = "(?<=From:\\s?)+[A-Za-z.\\s]+(?<=<?)"; //Sender name
        patterns[1] = "(?<=From:\\s?).*([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)"; //Sender email
        patterns[2] = "(?<=To:\\s?)+[A-Za-z.\\s]+(?<=<?)"; //Receiver name
        patterns[3] = "(?<=To:\\s?).*([a-zA-Z0-9._-])+@eng\\.jfn\\.ac\\.lk"; //Receiver email
        patterns[4] = "(?<=Sent:\\s?)+[A-Za-z0-9,\\s]+\\s+2022"; //Email date
        patterns[5] = "[EMC]+[EMC]+[1-9]+[0-9]+[0-9]+[0-9]"; //Course code
        patterns[6] = "(?<=[EMC]+[EMC]?)+[1-9]"; //Semester
        patterns[7] = "(?<=lab\\s?)+[0-9]+[0-9]"; //Lab no
        patterns[8] = "“*([a-zA-Z\s]++)*”"; //Lab name

        for(int i=0;i< details.length;i++) {

            Pattern p = Pattern.compile(patterns[i]);
            Matcher matcher = p.matcher(email);
            boolean found = false;
            while (matcher.find()) {
                details[i] = matcher.group();
                found = true;
            }
            if (!found) {
                System.out.println("I did not found the pattern in the given expression!" + patterns[i]);
            }

        }

        details[1] = details[1].substring(details[0].length() + 1); //Get the senders email
        details[3] = details[3].substring(details[2].length() + 1); //Get the receivers email

        singleMailDetails = "Sender Name: " + details[0] + "\n" +
                "Sender Email: " + details[1] + "\n" +
                "Receiver Name: " + details[2] + "\n" +
                "Receiver Email: " + details[3] + "\n" +
                "Email Date: " + details[4] + "\n" +
                "Course Code: " + details[5] + "\n" +
                "Semester: " + details[6] + "\n" +
                "Lab No: " + details[7] + "\n" +
                "Lab Name: " + details[8] + "\n\n";

        return singleMailDetails;
    }

    public static void main(String[] args) throws IOException {

        String[] fileNames = {"Email-01.txt", "Email-02.txt", "Email-03.txt", "Email-04.txt", "Email-05.txt", "Email-06.txt", "Email-07.txt", "Email-08.txt", "Email-09.txt", "Email-10.txt"};
        String[] emails = new String[10];
        String[] filteredDetails = new String[10];
        int i = 0;
        for(String fileName: fileNames){
            emails[i] = readFile(fileName);
            i++;
        }
        i = 0;
        for(String email: emails){
            filteredDetails[i] = filterDetails(email);
            i++;
        }
        //Write extracted data to a file one by one using for each loop
        for(String filteredDetail: filteredDetails){
            //System.out.print(filteredDetail);
            writeFile(filteredDetail);
        }

    }
}

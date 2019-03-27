import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class RainfallDataProcessor {
    public String fileName = "C:/Users/u6070565/Documents/src/cru-ts-2-10.1991-2000-cutdown.pre";
    private String StartYear;
    private String EndYear;
    private int YearDifference;


    public void ProcessRainfallData() throws IOException {
        File file = new File(fileName);
        FileInputStream InputStream = new FileInputStream(file);
        InputStreamReader InputstreamReader = new InputStreamReader(InputStream);
        BufferedReader br = new BufferedReader(InputstreamReader);




        String line;
        Integer LineCount=0;
        //Long lineCount = Files.lines(Paths.get(fileName)).count();


        String[] ReferenceNumbers;
        String[] Value;
        String[] ReferenceNumberElement=null;
        //RainfallData[] rainfallrecords = new RainfallData[];
        ArrayList<RainfallData> RainfallDataCollection = new ArrayList<>();
        int year = 1991;
        int day = 1;
        int count = 0;

        processHeaderData(br);

        while ((line = br.readLine()) != null) {


            //process the line
            RainfallData rainfallrecord = new RainfallData();
            boolean isGridRefInfo= LineCount % 11 == 0;

            if (isGridRefInfo)
            {
                ReferenceNumbers = line.split("= ");
                //System.out.println("this is line:" + line +"end of line");
                //System.out.println(ReferenceNumbers.length);
                ReferenceNumberElement = ReferenceNumbers[1].split(",");
                //System.out.println(Arrays.toString(ReferenceNumberElement));
                rainfallrecord.XRef = Integer.parseInt(ReferenceNumberElement[0].trim());
                rainfallrecord.YRef = Integer.parseInt(ReferenceNumberElement[1].trim());
                year++;

            }
            else
            {
                Value = line.split("(\\s)+");

                //System.out.println("Number of values: " + Value.length);
                for (int month = 1; month < Value.length; month++)
                {
                    System.out.println("Values: "+ Arrays.toString(Value));
                    rainfallrecord.CalendarDate = (month) + "/1/" + year;
                    System.out.println("Date: "+ rainfallrecord.CalendarDate);
                    rainfallrecord.CorrespondingValue = Integer.parseInt(Value[month].trim());
                    System.out.println("Value: "+ rainfallrecord.CorrespondingValue);
                }

            }
            LineCount++;
            RainfallDataCollection.add(LineCount-1, rainfallrecord);
            //System.out.printf("XRef: %s YRef: %s Date: %s Value: %s", rainfallrecord.XRef, rainfallrecord.YRef, rainfallrecord.CalendarDate, rainfallrecord.CorrespondingValue);
            //System.out.println(LineCount);
        }
        br.close();
    }


    public void processHeaderData(BufferedReader File) throws IOException {

        //probably find a way to determine header length from the file
        int headerlength= 5;
        for (int count=0; count < headerlength; count++ )
        {
         String Line= File.readLine();
         determineYearsfromHeaderData(Line);
        }
    }

    public void determineYearsfromHeaderData (String LineinFile) throws IOException {

            boolean LineContainsYears =LineinFile.contains("Years");
            if (LineContainsYears)
            {
                String[] LineContainingYears= LineinFile.split("\\] \\[");
                String[] RangeofYears= LineContainingYears[1].split("=");
                String[] StartandEndYears= RangeofYears[1].split("-");
                StartYear= StartandEndYears[0];
                EndYear= StartandEndYears[1];
                YearDifference = Integer.parseInt(EndYear) - Integer.parseInt(StartYear);

            }
    }

    public static void main(String[] args)  {
        RainfallDataProcessor processor = new RainfallDataProcessor();
        try {
            processor.ProcessRainfallData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


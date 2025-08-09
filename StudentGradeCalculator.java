import java.util.Scanner;

public class StudentGradeCalculator{
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
         
        System.out.println("****** Grade Calulator *******");
        System.out.println("Please, Enter The Number of subjects : ");
        int totalSubjects=scan.nextInt();

        int[] marks=new int[totalSubjects];
        String[] grades={"A+","A","B+","B","C+","C","D","F"};
        String[] masses={"Excellent","Very Good","Good","Above Average","Average","Satisfactory","Passing","Fail"};
        int totalMarks=0;
        System.out.println("Enter the marks ...");
        for (int i=0;i<totalSubjects;i++){
            System.out.println("Subject "+(i+1)+" : ");
            int mark=scan.nextInt();
            marks[i]=mark;
            totalMarks+=mark;
        }

        String grade="";
        String mass="";
        float total=totalMarks;
        float subj=totalSubjects;
        float avgPercentage = total/subj;
        if(avgPercentage<=100 && avgPercentage>=90){
            grade=grades[0];   
            mass=masses[0];
        }else if(avgPercentage<=89 && avgPercentage>=80){
            grade=grades[1]; 
            mass=masses[1];
        }else if(avgPercentage<=79 && avgPercentage>=70){
            grade=grades[2];
            mass=masses[2];  
        }else if(avgPercentage<=69 && avgPercentage>=60){
            grade=grades[3];
            mass=masses[3];    
        }else if(avgPercentage<=59 && avgPercentage>=50){
            grade=grades[4];
            mass=masses[4];   
        }else if(avgPercentage<=49 && avgPercentage>=40){
            grade=grades[5];
            mass=masses[5];   
        }else if(avgPercentage<=39 && avgPercentage>=33){
            grade=grades[6];
            mass=masses[6];   
        }else{
            grade=grades[7];
            mass=masses[7];
        }
        
        System.out.println();
        System.out.println("Total Marks : "+totalMarks);
        System.out.println("Average Percentage : "+avgPercentage +"%");
        System.out.println("Grade : " +grade +"  *"+mass);

        scan.close();
        System.exit(0);

    }
}
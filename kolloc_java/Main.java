import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
 
class Main
{
    public static void main(String[] args)
    {
        //String  fileNames[]={"1305.1.txt","1305.06.txt","1305.08.txt","1305.11.txt","1305.12.txt","1305.13.txt","1305.18.txt","1307.1.txt","1307.4.txt","1307.6.txt","1307.7.txt","1307.8.txt","1307.13.txt","1307.14.txt","1307.22.txt","1307.15.txt","1307.16.txt","1307.23.txt","1307.29.txt","1308.2.txt","1308.3.txt","1308.4.txt","1308.5.txt","1308.10.txt","1308.11.txt","1308.21.txt","1308.13.txt","1308.14.txt","1308.17.txt","1308.26.txt","1306.10.txt","1306.12.txt","1306.16.txt","1306.22.txt","1306.23.txt"};
        File folder = new File("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_java/tests");
        File[] files = folder.listFiles();
        for(int i =0; i<files.length; i++){
            Function(files[i].getName());
        }
        //System.out.println();
        //Function("1307.13.txt");
        
    }
    public static void Function(String fName){
        File file = new File("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_java/tests/"+fName);
        if (file.exists()){
            long startTime = System.nanoTime();
            int n =0;
            boolean Alphabet[] = new boolean[26];
            boolean result = true;

            ArrayList<Character> ch_l = new ArrayList<>();

            for (int  i =0; i< 26; i++){
                Alphabet[i]=true;
            }
            ArrayList<String> DNF_str = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file)))
            {
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.charAt(0)!='#'){
                        DNF_str.add(line+".");
                        n++;
                    }
                }
                //System.out.println(n);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i<n; i++){
                if (DNF_str.get(i).charAt(1) == '_'){
                    ch_l.add(DNF_str.get(i).charAt(0));
                }
            }

            for (int i = 0; i<n; i++){
                if (DNF_str.get(i).charAt(0) == '_'){
                    String edStr = DNF_str.get(i);
                    int m =1;
                    while(ch_l.contains(edStr.charAt(m))){
                        m++;
                    } 
                    Alphabet[edStr.charAt(m)-'a'] = false;
                }
            }

            boolean DNF[] = new boolean[n];
            for (int i =0; i<n; i++){
                DNF[i] = false;
            }
            for (int i =0; i<n; i++){
                String str = DNF_str.get(i);
                int k =0;
                //System.out.println(str);
                while(str.charAt(k) != '_'){
                    if ((str.charAt(k)>='a') & (str.charAt(k)<='z')){
                        DNF[i] = DNF[i] | Alphabet[str.charAt(k)-'a'];
                        //System.out.print(str.charAt(k));
                        k++;
                    }
                }
                k++;
                //System.out.println();
                while(str.charAt(k)!='.'){
                    DNF[i] = DNF[i] | !Alphabet[str.charAt(k)-'a'];
                    //System.out.print(str.charAt(k));
                    k++;
                    
                }
            }
             for (int i =0; i<n; i++){
                 //System.out.println(i+1 + " " + DNF[i]);
             }
            for (int i =0; i<n; i++){
                result = result&DNF[i];
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000;  //divide by 1000000 to get milliseconds.

            System.out.println(fName +" "+ result+" "+duration);
        }
        // else{
        //     System.out.println(fName+" "+"file not exist");
        // }
    }
}
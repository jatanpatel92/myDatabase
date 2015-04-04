/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 *
 * @author Jatan
 */
public class mydatabase 
{

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    static long offset;
    static Map<String, List<String>> person;
    static Map<String, List<String>> id;
    static Map<String, List<String>> state;
    static Map<String, List<String>> lname;
    static RandomAccessFile file;
    static RandomAccessFile file1;
    static Scanner sc;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        person = run("us-500.csv",",");
        initialize();
        count();
        select("id","10");
        select("lname","Butt");
        select("state","TX");
        insert();
        delete("state","TX");
        count();
        modify("1","fname","Jatan");
        modify("403","zip","75252");
        select("zip","75252");
    }
    static void select(String by,String value) throws IOException
    {
        file1 = new RandomAccessFile("data.db","rw");
        List<String> pos = new ArrayList<String>();
        System.out.println("Select Feature:");
        System.out.println("ID  First_Name  Last_Name  Company  Address  City  Country  State  ZIP  Phone1  Phone2  Email  Web");
        if(by=="id" || by=="lname" || by=="state")
        {
            if(by=="id")
            pos = id.get(value);
            else if(by=="lname")
            pos = lname.get(value);
            else if(by=="state")
            pos = state.get(value);
            for(int i=0;i<pos.size();i++)
            {
                file1.seek(Integer.parseInt(pos.get(i)));
                String r1 = file1.readLine();
                StringTokenizer st = new StringTokenizer(r1,":");
                String r2 = st.nextToken();
                st = new StringTokenizer(r1,"+");
                int j = 0;
                while(st.hasMoreTokens())
                {
                    if(j>0)
                    r2+="  "+st.nextToken();
                    else
                    r1 = st.nextToken();
                    ++j;
                }
                System.out.println(r2);
            }
            file1.close();
        }
        else
        {
            int fn = -1;
            if(by=="fname")
                fn = 0;
            else if(by=="company")
                fn=2;
            else if(by=="address")
                fn=3;
            else if(by=="city")
                fn=4;
            else if(by=="country")
                fn=5;
            else if(by=="zip")
                fn=7;
            else if(by=="phone1")
                fn=8;
            else if(by=="phone2")
                fn=9;
            else if(by=="email")
                fn=10;
            else if(by=="web")
                fn=11;
            List<String> keys = new ArrayList<String>();
            String str = null;
            for (Map.Entry<String, List<String>> entry : person.entrySet()) 
            {
                String key = entry.getKey();
                List<String> values1 = entry.getValue();
                if(values1.get(fn).equals(value))
                {
                    keys.add(key);
                    str = key;
                    for(int i=0;i<values1.size();i++)
                    {
                        str+="  "+values1.get(i);
                    }
                    System.out.println(str);
                }
            }
        }
    }
    static void modify(String idd, String field_name, String newVal) throws IOException
    {
        System.out.println("Modify Feature:");
        if(!person.containsKey(idd))
        {
            System.out.println("Please Enter valid Data!!");
        }
        else
        {
            int fn = -1;
            if(field_name=="fname")
                fn = 0;
            else if(field_name=="lname")
                fn=1;
            else if(field_name=="company")
                fn=2;
            else if(field_name=="address")
                fn=3;
            else if(field_name=="city")
                fn=4;
            else if(field_name=="country")
                fn=5;
            else if(field_name=="state")
                fn=6;
            else if(field_name=="zip")
                fn=7;
            else if(field_name=="phone1")
                fn=8;
            else if(field_name=="phone2")
                fn=9;
            else if(field_name=="email")
                fn=10;
            else if(field_name=="web")
                fn=11;
            List<String> vals = new ArrayList<String>();
            vals = person.get(idd);
            if(fn>-1)
            {
                vals.set(fn, newVal);
            }
            else
            {
                delete("id","1");
                String str = newVal;
                for(int i=0;i<vals.size();i++)
                    str+=","+vals.get(i);
                insert(str);
            }
            initialize();
            System.out.println("Record has been Successfully Modified!!");
        }
    }
    static void count()
    {
        System.out.println("Total Number of Records in the database : "+person.size());
    }
    static void delete(String by, String value) throws IOException
    {
        System.out.println("Delete Feature:");
        if(by=="id")
        {
            if(person.containsKey(value))
            {
                person.remove(value);
                initialize();
                System.out.println("Record has been Deleted Successfully!!");
            }
            else
            System.out.println("Please enter valid data!");
        }
        else
        {
            int fn = 0;
            if(by=="fname")
                fn = 0;
            else if(by=="lname")
                fn=1;
            else if(by=="company")
                fn=2;
            else if(by=="address")
                fn=3;
            else if(by=="city")
                fn=4;
            else if(by=="country")
                fn=5;
            else if(by=="state")
                fn=6;
            else if(by=="zip")
                fn=7;
            else if(by=="phone1")
                fn=8;
            else if(by=="phone2")
                fn=9;
            else if(by=="email")
                fn=10;
            else if(by=="web")
                fn=10;
            List<String> keys = new ArrayList<String>();
            for (Map.Entry<String, List<String>> entry : person.entrySet()) 
            {
                String key = entry.getKey();
                List<String> values1 = entry.getValue();
                if(values1.get(fn).equals(value))
                keys.add(key);
            }
            if(keys.size()>0)
            {
                for(int i=0;i<keys.size();i++)
                person.remove(keys.get(i));
                initialize();
                System.out.println("Record has been Deleted Successfully!!");
            }
            else
            System.out.println("Please enter valid data!");
        }
    }
    static void insert() throws IOException
    {
        System.out.println("Insert Feature:");
        boolean flag = false;
        System.out.println("Please Enter the Data in the appropiate form : ");
        sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.replaceAll("'", "");
        input = removeCharAt(input,0);
        input = removeCharAt(input,input.length()-1);
        System.out.println(input);
        StringTokenizer st = new StringTokenizer(input,",");
        if(st.countTokens()>13)
        flag = true;
        int j = 0;
        List<String> temp = new ArrayList<String>();
        String key = null;
        while(st.hasMoreTokens())
        {
            ++j;
            if(j==4)
            input = st.nextToken() + "," +st.nextToken();
            else
            input = st.nextToken();
            if(j==1)
            key = input;
            else
            temp.add(input);    
        }
        if(!person.containsKey(key))
        {
            person.put(key, temp);
            initialize();
            System.out.println("Record has been Inserted Successfully!!");
        }
        else
        {
            System.out.println("Record already exists!");
        }
    }
    static void insert(String input) throws IOException
    {
        boolean flag = false;
        StringTokenizer st = new StringTokenizer(input,",");
        if(st.countTokens()>13)
        flag = true;
        int j = 0;
        List<String> temp = new ArrayList<String>();
        String key = null;
        while(st.hasMoreTokens())
        {
            ++j;
            if(j==4)
            input = st.nextToken() + "," +st.nextToken();
            else
            input = st.nextToken();
            if(j==1)
            key = input;
            else
            temp.add(input);    
        }
        person.put(key, temp);
        initialize();
    }
    private static String removeCharAt(String s, int i) {
        StringBuffer buf = new StringBuffer(s.length() -1);
        buf.append(s.substring(0, i)).append(s.substring(i+1));
        return buf.toString();
    }
   
   static Map run(String csvPath, String ft) throws FileNotFoundException
    {
        BufferedReader br = null;
        String line = "";
       // String csvSplitBy = ",";
        Map<String, List<String>> person = new HashMap<String, List<String>>();
        try
        {

            br = new BufferedReader(new FileReader(csvPath));
            String key = null;
            int i = 0;
            while ((line = br.readLine()) != null)
            {
                if(i>0)
                {
                    StringTokenizer st = new StringTokenizer(line,",");
                    key = st.nextToken();
                    List<String> valSet = new ArrayList<String>();
                    while(st.hasMoreTokens())
                    {
                        valSet.add(st.nextToken());
                    }
                    person.put(key,valSet);
                }
                ++i;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally 
        {
            if (br != null) 
            {
                try 
                {
                    br.close();
                } catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        return person;
    }
    
    private static void initialize() throws FileNotFoundException, IOException
    {
        id = new HashMap<String, List<String>>();
        state = new HashMap<String, List<String>>();
        lname = new HashMap<String, List<String>>();
        File ff = new File("data.db");
        ff.delete();
        ff = new File("state.ndx");
        ff.delete();
        ff = new File("id.ndx");
        ff.delete();
        ff = new File("lname.ndx");
        ff.delete();
        file = new RandomAccessFile("data.db","rw");
        BufferedWriter bw1 = new BufferedWriter(new FileWriter("id.ndx"));
        //System.out.println(person.size());
        for (Map.Entry<String, List<String>> entry : person.entrySet()) 
        {
            String key = entry.getKey();
            List<String> values1 = entry.getValue();
            List<String> temp = new ArrayList<String>();
            List<String> temp1 = new ArrayList<String>();
            List<String> temp2 = new ArrayList<String>();
            offset = file.getFilePointer();
            file.writeBytes(key);
            file.writeBytes(":");
            bw1.write(""+key+":");
            bw1.write(""+offset);
            temp.add(offset+"");
            id.put(key, temp);
            for(int i=0;i<values1.size();i++)
            {
                String k = values1.get(i);
                file.writeBytes(k);
                file.writeBytes("+");
                if(i==1)
                {
                    //offset = file.getFilePointer();
                    if(lname.containsKey(k))
                    {
                        temp1 = lname.get(k);
                    }
                    if(!temp1.contains(key))
                    temp1.add(""+offset);    
                    lname.put(k,temp1);
                }
                else if(i==6)
                {
                    if(state.containsKey(k))
                    {
                        temp2 = state.get(k);
                    }
                    if(!temp1.contains(key))
                        temp2.add(""+offset);    
                    state.put(k,temp2);
                }
            }
            file.writeBytes("\n");
            bw1.write("\n");            
        }
        bw1.close();
        file.close();
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("lname.ndx"));
        for (Map.Entry<String, List<String>> entry : lname.entrySet()) 
        {
            String key = entry.getKey();
            List<String> values2 = entry.getValue();
            bw2.write(key);
            bw2.write(":");
            for(int i=0;i<values2.size();i++)
            {
                bw2.write(values2.get(i));
                bw2.write("+");
            }
            bw2.write("\n");
        }
        bw2.close();
        BufferedWriter bw3 = new BufferedWriter(new FileWriter("state.ndx"));
        for (Map.Entry<String, List<String>> entry : state.entrySet()) 
        {
            String key = entry.getKey();
            List<String> values3 = entry.getValue();
            bw3.write(key);
            bw3.write(":");
            for(int i=0;i<values3.size();i++)
            {
                bw3.write(values3.get(i));
                bw3.write("+");
            }
            bw3.write("\n");
        }
        bw3.close();
    }
}

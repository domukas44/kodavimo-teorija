//k pradinio vektoriaus ilgis
// 2^M nusako generuojancios matricos stulpeliu skaiciu, m - vektoriu skaiciu matricoj skaiciuojant nuo v1

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


        System.out.println("Type 1 for part 1, 2 for part 2 or 3 for part 3:");
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        String str= sc.nextLine();
        int part = Integer.parseInt(str);
        String[] splitData = new String[4];
        ArrayList<String> secondData = new ArrayList<>();

        //load image 3 dalis
        BufferedImage image = null;
        int width = 320;    //width of the image
        int height = 240;   //height of the image
        File input_file;



        switch (str) {
            case "1":


                try {

                    File myObj = new File("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\1dalis.txt");
                    Scanner myReader = new Scanner(new FileReader(myObj));
                    String data = myReader.nextLine();
                    splitData = data.split(" ");


                } catch (FileNotFoundException e) {
                    System.out.println("Nuskaitymo klaida");
                    e.printStackTrace();
                }
                break;
            case "2":
                try {

                    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\2dalis.txt"));
                    String data = br.readLine();
                    splitData = data.split(" ");
                    while ((data = br.readLine()) != null) {
                        secondData.add(data);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            case "3":

                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\3dalis.txt"));
                String data = br.readLine();
                splitData = data.split(" ");

                //URL path2 = Main.class.getResource("out.bmp");

                input_file = new File("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\out.bmp");
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                image = ImageIO.read(input_file);


                // Writing to file taking type and path as
                ImageIO.write(image, "bmp", new File("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\paveiksliukas.bmp"));


                break;
            default:
                System.out.println("blogai ivesta");
                System.exit(0);
                break;
        }

        //m r % vector - split data elementai

        int r = Integer.parseInt(splitData[1]), m = Integer.parseInt(splitData[0]), t = Integer.parseInt(splitData[2]);
        String info="";
        if (part==1)
            info = splitData[3];

        int Columns = (int) Math.pow(2,m);

        //susikuriu a vectoriu masyva aValues
        String[] aValues = new String[Columns];

        //uzpildau a vectoriu masyva
        String temp2;
        int aNum = 0;
        for( int i=0; i<Math.pow(2,m); i++)
        {
            temp2 = Integer.toBinaryString(aNum);

            //jei mazesnis uz m, pridedu nuliu pradzioje
            while(temp2.length()<m)
                temp2 = '0' + temp2;
            aValues[i] = temp2;
            aNum++;
        }

        //skaiciuojam kiek bus v (t.y. eiluciu)
        int Rows = 1;

        //jei r daugiau uz 0, tai pridedu pradinius vektorius, pvz.: v1 v2 v3 ir t.t
        if(r>0)
            Rows+=m;

        //pridedu reikalingas v kombinacijas
        for(int i=2; i<=r; i++)
        {
            //combination grazina skaiciu galimu sandaugu m elementu
            Rows+= combination(m,i);
        }
        if (info.length()!=Rows && str.equals("1"))
        {
            System.out.println("wrong input size, correct is: " + Rows );
            System.exit(0);
        }

        //sugeneruojama RM matrica

        int[][] Matrix = new int[Rows][Columns];
        ArrayList<String> filteredMatrix = new ArrayList<String>();

        //uzpildau v0 eilute vienetais
        for (int i=0; i<Columns; i++)
            Matrix[0][i]=1;

        //jei r >0, tai reikes naudoti ir v1 v2 ir t.t
        if(r>0)
        {

            //padarom vienetinius v, pvz>: v1 v2 ir t.t.
            for (int i = 1; i <= m; i++)
            {
                for (int j = 0; j < Columns; j++)
                {
                    if(aValues[j].startsWith("0", i-1))

                        Matrix[i][j]=1;
                    else
                        Matrix[i][j]=0;

                }
            }
            if(r>1) {


                //bandau sukurti binary matrica


                for(int i=0;i<Columns; i++)
                {
                    String temp =Integer.toBinaryString(i);
                    while(temp.length()<m)
                        temp = '0' + temp;
                    StringBuilder sb = new StringBuilder(temp);
                    sb.reverse();
                    temp=sb.toString();

                    //skaiciuoju kiek temp turi vienetu
                    int count = temp.length() - temp.replaceAll("1", "").length();

                    //darau kazkoki algoritma kur skaiciuoja kiek yra vienetu ir jei neperdaug ir nepermazai ideda i matrica, tai cia turetu buti ka su kuo dauginsiu kai darysiu kombinacijas pvz.: v1*v2 v2*v3 ir t.t.
                    if(count>1 && count<=r)
                    {
                        int amountOfOnes=0;
                        for (int u=0; u<filteredMatrix.size();u++)
                        {

                            int tempInt = filteredMatrix.get(u).length() - filteredMatrix.get(u).replaceAll("1", "").length();
                            if (amountOfOnes<tempInt)
                                amountOfOnes=tempInt;
                        }

                        if(count>=amountOfOnes)
                        {
                            filteredMatrix.add(temp);
                        }

                        else {

                            for (int u=0; u<filteredMatrix.size();u++)
                            {
                                if (count<filteredMatrix.get(u).length() - filteredMatrix.get(u).replaceAll("1", "").length())
                                {
                                    filteredMatrix.add(u,temp);
                                    //System.out.println("adding "+ temp);
                                    break;
                                }
                            }
                        }

                    }

                }

                //sunkus algoritmas, kuris uzpildo matricos likusias eilutes

                int counter = 0;
                for(int i=m+1; i<Rows; i++)
                {
                    String whatToMultiply = filteredMatrix.get(counter);
                    for (int j=0; j<Columns; j++)
                    {
                        int multipliedV =1;
                        for(int l =0; l<m; l++)
                        {
                            if(whatToMultiply.startsWith("1", l))
                            {
                                multipliedV*=Matrix[l+1][j];
                            }
                        }
                        Matrix[i][j] = multipliedV;
                    }
                    counter++;
                }

            }

        }


        //pridedu v1-vi i array
        for (int i=m; i>0; i--)
        {

            String temp ="0";
            while(temp.length()<m)
                temp = '0' + temp;
            StringBuilder sb = new StringBuilder(temp);
            sb.setCharAt(i-1, '1');
            temp = sb.toString();

            filteredMatrix.add(0,temp);
        }
        //ir v0
        StringBuilder temp3 = new StringBuilder("0");
        while(temp3.length()<m)
            temp3.insert(0, '0');
        filteredMatrix.add(0, temp3.toString());

        //1 dalis = uzkodavimas
        if (part==1)
        {
            String codedVector = code(Columns,Rows,Matrix,info);
            System.out.println("coded vector: " +codedVector);
            //2 dalis

            System.out.println("sending vector through tunnel...");
            String infoAfterTunnel = Tunnel(codedVector,t,true);



            System.out.println("received vector: " + infoAfterTunnel);
            System.out.println("do u want to change the vector?  y / n");
            str= sc.nextLine();
            if (str.equals("y"))
            {
                System.out.println("type new vector:");
                str= sc.nextLine();
                infoAfterTunnel=str;
            }


            //3dalis
            System.out.println("decoded vector:");
            System.out.println(decode(Rows,Columns,m,filteredMatrix,infoAfterTunnel,r,aValues,Matrix));

        }
        //siunciamas zodinis pranesimas
        else if (part==2)
        {

            ArrayList<String> finalDataInListNotCoded = new ArrayList<>();
            ArrayList<String> finalDataInListCoded = new ArrayList<>();
            //pasiverciam i bitus, uzkoduojam, prasiunciam pro tuneli dekoduojam kiekviena eilute atskirai

            for (String s: secondData)
            {
                int additionalBytes;
                byte[] bytes = s.getBytes();
                StringBuilder binary = new StringBuilder();
                StringBuilder temp = new StringBuilder();
                StringBuilder finalMsg = new StringBuilder();
                StringBuilder tempCoded = new StringBuilder();
                StringBuilder tempByVectors = new StringBuilder();

                //pasiverciam i bitus
                for (byte b : bytes)
                {
                    int val = b;
                    for (int i = 0; i < 8; i++)
                    {
                        binary.append((val & 128) == 0 ? 0 : 1);
                        val <<= 1;
                    }
                }
                boolean go = true;

                //neuzkoduota prasiunciame tuneliu vektoriais
                tempByVectors.append(binary);
                while(tempByVectors.length()>0)
                {
                    if (tempByVectors.length()>Rows)
                        temp.append(Tunnel(tempByVectors.substring(0, Rows),t,false));
                    else temp.append(Tunnel(tempByVectors.substring(0, tempByVectors.length()),t,false));
                    tempByVectors.replace(0,Rows,"");
                }

                StringBuilder tmp2 = new StringBuilder();
                String st="";

                //uzkoduojam ir prasiunciam tuneliu ir dekoduojam
                while(go)
                {
                    if (binary.length()>=Rows)
                    {
                        tmp2.append(code(Columns,Rows,Matrix,binary.substring(0,Rows)));
                        st = Tunnel(tmp2.toString(),t,false);

                        tempCoded.append(decode(Rows,Columns,m,filteredMatrix,st,r,aValues,Matrix));
                        binary.replace(0,Rows,"");
                        tmp2.setLength(0);
                    }

                    // pridedam bitu, jei reikia
                    else if (binary.length()>0)
                    {
                        additionalBytes=Rows-binary.length();
                        for (int j=0; j<additionalBytes; j++)
                        {
                            binary.append("0");

                        }
                        tmp2.append(code(Columns,Rows,Matrix,binary.substring(0,Rows)));
                        st = Tunnel(tmp2.toString(),t,false);

                        tempCoded.append(decode(Rows,Columns,m,filteredMatrix,st,r,aValues,Matrix));
                        if (additionalBytes>0)
                        {
                            tempCoded.replace(tempCoded.length()-additionalBytes,tempCoded.length(),"");
                        }
                        binary.replace(0,Rows,"");
                        tmp2.setLength(0);
                        go=false;
                    }
                    else go=false;
                }

                //to letters from not coded
                while (temp.length()>=8)
                {
                    int charCode = Integer.parseInt(temp.substring(0,8), 2);
                    String letter = Character.toString((char) charCode);
                    finalMsg.append(letter);
                    temp.replace(0,8,"");

                }

                //to letters from decoded
                while (tempCoded.length()>=8)
                {
                    int charCode = Integer.parseInt(tempCoded.substring(0,8), 2);
                    String letter = Character.toString((char) charCode);

                    //reused binary cuz it is empty
                    binary.append(letter);
                    tempCoded.replace(0,8,"");

                }
                tempCoded.append(binary);

                finalDataInListCoded.add(tempCoded.toString());
                finalDataInListNotCoded.add(finalMsg.toString());


            }
            System.out.println("received text, which was not coded: ");
            for (String s:finalDataInListNotCoded)
                System.out.println(s);
            System.out.println();
            System.out.println("received text, which was coded: ");
            for (String s: finalDataInListCoded)
                System.out.println(s);

        }
        else {

            StringBuilder notCodedtest = new StringBuilder();

            int additionalBytes;

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "bmp", bos );

            byte [] bytes = bos.toByteArray();
            StringBuilder binary = new StringBuilder();
            for (byte b: bytes)
            {
                binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }
            notCodedtest.append(binary);


            StringBuilder temp = new StringBuilder();
            StringBuilder tempCoded = new StringBuilder();
            StringBuilder tempByVectors = new StringBuilder();

            boolean go = true;

            //neuzkoduota prasiunciame tuneliu vektoriais
            tempByVectors.append(binary);
            String header=tempByVectors.substring(0,432);
            tempByVectors.replace(0,432,"");
            temp.append(header);
            while(tempByVectors.length()>0)
            {
                if (tempByVectors.length()>Rows)
                    temp.append(Tunnel(tempByVectors.substring(0, Rows),t,false));
                else temp.append(Tunnel(tempByVectors.substring(0, tempByVectors.length()),t,false));
                tempByVectors.replace(0,Rows,"");
            }

            StringBuilder tmp2 = new StringBuilder();
            String st="";
            tempCoded.append(binary.substring(0,432));
            binary.replace(0,432,"");

            //uzkoduojam ir prasiunciam tuneliu ir dekoduojam
            while(go)
            {

                //System.out.println(binary.length());
                if (binary.length()>=Rows)
                {
                    tmp2.append(code(Columns,Rows,Matrix,binary.substring(0,Rows)));
                    st = Tunnel(tmp2.toString(),t,false);

                    tempCoded.append(decode(Rows,Columns,m,filteredMatrix,st,r,aValues,Matrix));
                    binary.replace(0,Rows,"");
                    tmp2.setLength(0);
                }

                // pridedam bitu, jei reikia
                else if (binary.length()>0)
                {
                    additionalBytes=Rows-binary.length();
                    for (int j=0; j<additionalBytes; j++)
                    {
                        binary.append("0");

                    }
                    tmp2.append(code(Columns,Rows,Matrix,binary.substring(0,Rows)));
                    st = Tunnel(tmp2.toString(),t,false);

                    tempCoded.append(decode(Rows,Columns,m,filteredMatrix,st,r,aValues,Matrix));
                    if (additionalBytes>0)
                    {
                        tempCoded.replace(tempCoded.length()-additionalBytes,tempCoded.length(),"");
                    }
                    binary.replace(0,Rows,"");
                    tmp2.setLength(0);
                    go=false;
                }
                else go=false;
            }

            byte[] imgCoded = new BigInteger(tempCoded.toString(), 2).toByteArray();
            byte[] img = new BigInteger(temp.toString(), 2).toByteArray();

            ByteArrayInputStream bis = new ByteArrayInputStream(img);
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "bmp", new File("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\notCoded.bmp"));
            System.out.println("image created");

            BufferedImage imagCoded=ImageIO.read(new ByteArrayInputStream(imgCoded));
            ImageIO.write(imagCoded, "bmp", new File("C:\\Users\\Vartotojas\\Desktop\\kodavimoLogika\\src\\coded.bmp"));
            System.out.println("decoded image created");

        }
    }


    public static int combination(int n, int k)
    {

        return (factorial(n)/(factorial(k)*factorial(n-k)));
    }

    public static int factorial(int number) {
        int result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }
    public static String Tunnel(String codedV, int probability, boolean print)

    {
        StringBuilder tamperedInfo= new StringBuilder();
        Random rand = new Random();
        int randNum;
        int mistakes=0;
        if(print)
        System.out.println("mistakes at positions: ");
        for (int i=0; i<codedV.length();i++)
        {
            randNum = rand.nextInt(10000);
            if (randNum<probability)
            {
                int temp = Integer.parseInt(String.valueOf(codedV.charAt(i)));
                temp= (temp+1)%2;
                tamperedInfo.append(temp);
                int tmp = i+1;
                if (print)
                    System.out.print(tmp + " ");
                mistakes++;

            }
            else
                tamperedInfo.append(codedV.charAt(i));

        }

        if (print)
        {
            System.out.println();
            System.out.println("there were "+mistakes + " mistakes");
        }
        return tamperedInfo.toString();
    }

    //2dalis
    public static String code(int Columns, int Rows, int[][] Matrix, String info)
    {
        //uzkoduojam pradine informacija pasinaudodami matrica
        StringBuilder codedVector = new StringBuilder();
        for(int i=0; i<Columns; i++)
        {
            int forCalculations =0;
            for (int j=0; j<Rows; j++)
            {
                forCalculations+=Matrix[j][i]*Integer.parseInt(String.valueOf(info.charAt(j)));
            }
            forCalculations=forCalculations%2;
            codedVector.append(forCalculations);
        }
        return codedVector.toString();
    }

    //3dalis
    public static String decode(int Rows,int Columns, int m,ArrayList<String> filteredMatrix, String infoAfterTunnel, int r,String[] aValues, int[][] Matrix)
    {

        int [] decodedV = new int[Rows];


        int currRow=Rows;

        //prasideda decodavimas


        StringBuilder sb = new StringBuilder(infoAfterTunnel);

        //sukam su kiekvienu r kombinaciju t.y. pirma su pvz v1*v2*v3, tada su v1*v2, tada su v1*v3 ir t.t.

        for (int i=r; i>=0; i--)
        {
            int times = combination(m,i);
            currRow-=times;

            //sukam to paties sandaugu skaiciaus daugybas
            for (int j=0; j<times; j++)
            {

                ArrayList<Integer> ls = new ArrayList<>(); // l'ų masyvas
                ArrayList<String> ts = new ArrayList<>(); // t reiksmiu masyvas
                ArrayList<String> ws = new ArrayList<>(); // w reiksmiu masyvas


                String specificColumn = filteredMatrix.get(currRow+j);

                //pasiziurim kas bus l ir jas susidedam i masyva
                for (int l=0; l<m; l++)
                {
                    if (specificColumn.charAt(l) == '0')
                    {
                        ls.add(l);

                    }
                }

                StringBuilder temp;

                if (!ls.isEmpty())
                {
                    //pasiskaiciuojam ts ir susidedam i masyva
                    int tSize = ls.size();
                    int wSize = (int) Math.pow(2,tSize);
                    for (int l = 0; l<wSize; l++)
                    {
                        temp = new StringBuilder(Integer.toBinaryString(l));
                        while(temp.length()<tSize)
                            temp.insert(0, '0');
                        ts.add(temp.toString());
                    }

                    //suku tsize(tiek bus w skirtingu) kartu
                    for (int k=0; k<wSize; k++)
                    {
                        StringBuilder tempW= new StringBuilder();
                        String curT= ts.get(k);
                        boolean good = true;
                        String curA;
                        int curL;


                        for (String aValue : aValues)
                        {
                            curA = aValue;
                            for (int o = 0; o < ls.size(); o++)
                            {
                                curL=ls.get(o);

                                if (curA.charAt(curL) != curT.charAt(o))
                                {
                                    good=false;

                                }
                            }
                            if (good)
                                tempW.append("1");
                            else tempW.append("0");
                            good=true;

                        }
                        ws.add(tempW.toString());
                    }

                    Integer[] decoded = new Integer[ws.size()];
                    for (int u=0; u<decoded.length; u++)
                    {
                        String curW = ws.get(u);
                        int countDeco=0;
                        for (int o=0; o<Columns;o++) //Rows????
                        {
                            countDeco+=Integer.parseInt(String.valueOf(curW.charAt(o)))*sb.toString().charAt(o);
                        }
                        countDeco%=2;
                        decoded[u]=countDeco;
                    }
                    int zeros=0,ones=1;
                    for (int deco:decoded)
                    {
                        if (deco==1)
                            ones++;
                        else zeros++;
                    }

                    if (zeros>ones)
                        decodedV[currRow+j]=0;
                    else if(ones>zeros) decodedV[currRow+j]=1;
                    else decodedV[currRow+j]=0;  //change, because it cant know the correct answer -----------------------------------------------------------------------------------

                }
                else
                {
                    StringBuilder tempws= new StringBuilder();
                    for (int u=0; u<aValues.length;u++)
                    {
                        tempws.append(1);
                    }
                    ws.add(tempws.toString());
                    int decoded = 0;

                    String curW = ws.get(0);

                    for (int o=0; o<Columns;o++)
                    {
                        decoded+=Integer.parseInt(String.valueOf(curW.charAt(o)))*sb.toString().charAt(o);
                    }
                    decoded%=2;

                    decodedV[currRow+j]=decoded;
                }

            }

            //cia jau bandom atimti is uzkoduoto vektoriaus paskutines daugybos eilutes

            if (i>0)
            {
                StringBuilder sub= new StringBuilder();
                int[] subtraction = new int[aValues.length];
                boolean empty = true;

                for (int u=currRow; u<currRow+times; u++)
                {
                    if (decodedV[u]>=1)   //change to =
                    {
                        if (empty)
                        {
                            for (int o=0; o<aValues.length; o++)
                            {
                                subtraction[o]= Matrix[u][o];
                            }

                            empty=false;
                        }
                        else
                        {
                            for (int o=0; o<aValues.length; o++)
                            {
                                subtraction[o]= (subtraction[o] +Matrix[u][o]) %2;
                            }

                        }
                    }

                }

                for (int a:subtraction)
                {
                    sub.append(a);
                }
                int[] tmpArr = new int[aValues.length];

                for (int j=0; j<aValues.length; j++)
                {
                    tmpArr[j] = Integer.parseInt(String.valueOf(sub.charAt(j))) + Integer.parseInt(String.valueOf(sb.charAt(j)));
                    tmpArr[j]%=2;
                    sb.replace(j,j+1,String.valueOf(tmpArr[j]));

                }
            }

        }

        StringBuilder returnVal = new StringBuilder();
        for (int i:decodedV)
        {
            returnVal.append(i);
        }

        //grazinam dekoduota vektoriu
        return returnVal.toString();

    }

}

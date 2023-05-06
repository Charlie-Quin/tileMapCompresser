import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("input tilemap to compress (make sure to hit enter and then type \"fin\"): ");

        String inputStr = "";

        String temp = "";

        while(temp.indexOf("fin") == -1){
            inputStr += temp;
            temp = scan.nextLine();
        }

        inputStr += temp;

        System.out.println("inputStr: " + inputStr);

        ArrayList<Integer> simplifiedTileMap = new ArrayList<>();

        stringToList(simplifiedTileMap,inputStr);

        System.out.println("New list: (" + simplifiedTileMap.size() + ")\n" + simplifiedTileMap);

        ArrayList<Integer> compressedList = compressList(simplifiedTileMap);

        System.out.println("Compressed list: (" + compressedList.size() + ")\n" + compressedList);

        ArrayList<Integer> decompressedList = decompressList( compressedList);

        System.out.println("Decompressed list: (" + decompressedList.size() + ")\n" + decompressedList);


        System.out.println("Enter a name: ");

        String name = scan.nextLine();

        System.out.println("Enter a texture file path: ");

        String filePath = scan.nextLine();

        System.out.println("Enter width: ");

        int width = Integer.parseInt(scan.nextLine());

        System.out.println("Enter height: ");

        int height = Integer.parseInt(scan.nextLine());

        System.out.println("Enter tile size: ");

        int tileSize = Integer.parseInt(scan.nextLine());

        String output = "public static TileMapInfo " + name + " = new TileMapInfo(new Texture(\"" + filePath + "\"),";
        output += width + ",";
        output += height + ",";
        output += tileSize + ",";

        output += "decompressList(new int[]{" + compressedList.toString().substring(1,compressedList.toString().length()-1);
        output += "}));";

        System.out.println(output);




    }

    private static ArrayList<Integer> decompressList(ArrayList<Integer> input) {
        ArrayList<Integer> newArray = new ArrayList<>();

        for (int i = 1; i < input.size(); i+=2){

            for (int n = 0; n < input.get(i); n++){
                newArray.add(input.get(i-1));
            }

        }

        return  newArray;

    }

    private static ArrayList<Integer> decompressList(int[] input) {
        ArrayList<Integer> newArray = new ArrayList<>();

        for (int i = 1; i < input.length; i+=2){

            for (int n = 0; n < input[i]; n++){
                newArray.add(input[i-1]);
            }

        }

        return  newArray;

    }

    private static ArrayList<Integer> compressList(ArrayList<Integer> input) {

        ArrayList<Integer> newList = new ArrayList<>();


        for (int i = 0; i < input.size();i++ ){
            if (i == 0){
                newList.add(input.get(i) );
                newList.add(1);
                continue;
            }

            if (input.get(i) != input.get(i-1)){
                newList.add(input.get(i));
                newList.add(1);
                continue;
            }

            newList.set(newList.size()-1,newList.get(newList.size()-1) + 1);


        }

        return newList;
    }

    public static void stringToList(ArrayList<Integer> list,String input){

        String tempInput = input;
        list.clear();

        //18,54,5,0,8,43,85

        {
            int num = Integer.parseInt(tempInput.substring(0, 1));

            list.add(num == 0 ? 0 : 1);
        }

        int nextComma = tempInput.indexOf(",");

        while (nextComma != -1){
            //System.out.println("nextComma: " + nextComma + " tempInput: " + tempInput + " list: " + list);
            int num = -1;

            while (num == -1 && nextComma < tempInput.length()){
                try {
                    num = Integer.parseInt(tempInput.substring(nextComma+1,nextComma+2));
                }
                catch (Exception ex){
                    nextComma++;
                }
            }


            list.add(num == 0 ? 0 : 1);

            tempInput = tempInput.substring(nextComma+1);

            nextComma = tempInput.indexOf(",");

        }


    }




}
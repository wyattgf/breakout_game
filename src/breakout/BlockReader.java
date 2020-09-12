package breakout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BlockReader {

  //constants
  private static final String DIRECTORY = "data";
  private static final String DESIRED_FILE_NAME = "/initialFile.txt";

  //instance variables
  private List<Block> listOfBlocks;

  public BlockReader(){
    listOfBlocks = new ArrayList<Block>();
  }

  private void readCreateBlocks(){
    File file = new File(DIRECTORY+DESIRED_FILE_NAME);
    try{
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line = br.readLine();
      while (line!=null){
        Block b = createBlock(line);
        listOfBlocks.add(b);
      }
    }
    catch(Exception e){ }

  }


  public List<Block> getBlocks(){
    return listOfBlocks;
  }

  private Block createBlock(String fileLine){
    String[] blockData = fileLine.split(" ");
    Block b = new Block(Double.parseDouble(blockData[0]),Double.parseDouble(blockData[1]),Integer.parseInt(blockData[3]));
    return b;
  }
}

package breakout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockReader {

  //constants
  private static final String DIRECTORY = "data";
  private static final int BLOCK_MIN_VALUE = 1;
  private static final int BLOCK_MAX_VALUE = 4;
  //instance variables
  private List<Block> listOfBlocks;

  public BlockReader() {
    listOfBlocks = new ArrayList<>();
  }

  private void readCreateBlocks(String filename) {
    File file = new File(DIRECTORY + "/" +filename);
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        if (!line.equals("")) {
          Block b = createBlock(line);
          listOfBlocks.add(b);
        }
      }
    } catch (FileNotFoundException e) {

    }catch (IOException e){

    }

  }


  public List<Block> getBlocks(String filename) {
    readCreateBlocks(filename);
    return listOfBlocks;
  }

  private Block createBlock(String fileLine) {
    String[] blockData = fileLine.split(" ");
    return new DurableBlock(Double.parseDouble(blockData[0]), Double.parseDouble(blockData[1]));
  }
}

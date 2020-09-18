package breakout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BlockReader {

  //constants
  private static final String DIRECTORY = "data";
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
        Block b = createBlock(line);
        listOfBlocks.add(b);
      }
    } catch (Exception e) {
    }

  }


  public List<Block> getBlocks(String filename) {
    readCreateBlocks(filename);
    return listOfBlocks;
  }

  private Block createBlock(String fileLine) {
    String[] blockData = fileLine.split(" ");
    Block b = new Block(Double.parseDouble(blockData[0]), Double.parseDouble(blockData[1]),
        Integer.parseInt(blockData[2]));
    return b;
  }
}

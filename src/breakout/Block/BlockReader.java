package breakout.Block;

import breakout.PowerUp.PowerUp;
import breakout.PowerUp.PowerUpLife;
import breakout.PowerUp.PowerUpPaddleSize;
import breakout.PowerUp.PowerUpPaddleSpeed;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockReader {

  //constants
  private final List<Block> POSSIBLE_BLOCKS = List
      .of(new DurableBlock(0, 0), new PowerUpBlock(0, 0),
          new MovingBlock(0, 0));

  private static final String DIRECTORY = "data";
  private static final int BLOCK_XPOS = 0;
  private static final int BLOCK_YPOS = 1;
  private static final int TYPE_OF_BLOCK = 2;

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
    Block readBlock = POSSIBLE_BLOCKS.get(Integer.parseInt(blockData[TYPE_OF_BLOCK]));
    Block newBlock = readBlock.newBlock();
    newBlock.setX(Double.parseDouble(blockData[BLOCK_XPOS]));
    newBlock.setY(Double.parseDouble(blockData[BLOCK_YPOS]));
    return newBlock;
  }
}

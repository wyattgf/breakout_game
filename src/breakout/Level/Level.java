package breakout.Level;

import breakout.Block;
import breakout.BlockReader;
import java.util.List;

public class Level {
  private BlockReader blockReader;

  public Level(){
    blockReader = new BlockReader();
  }

  public List<Block> getBlocks(String filename){
    return blockReader.getBlocks(filename);
  }
}

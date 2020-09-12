package breakout;

import java.util.ArrayList;
import java.util.List;

public class FileReader {

  private List<Block> listOfBlocks;
  public FileReader(){
    listOfBlocks = new ArrayList<Block>();
  }
  public List<Block> getBlocks(){
    return listOfBlocks;
  }

  private void readCreateBlocks(){

  }

  private Block createBlock(String fileLine){
    //splitting here
  }
}

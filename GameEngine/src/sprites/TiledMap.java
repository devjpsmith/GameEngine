package sprites;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import core.IDrawable;


public class TiledMap implements IDrawable{
	protected int mapWidth = 32;			// how many tiles across the map is
	protected int tileSize = 32;			// the width of each tile
	protected int sourceWidthInTiles = 21;	// how many tiles across the source image is
	protected int[] tileArray;				// the gid array from which our map will be built

	protected BufferedImage mSource;
	
	public TiledMap(){
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("images/ground_tiles.png");
			mSource = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tileArray = new int[]{
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,175,176,176,176,176,176,176,176,176,176,176,176,176,177,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,
				176,176,176,199,197,197,197,197,197,197,197,197,197,197,197,197,200,176,177,66,66,66,66,66,66,66,66,66,66,66,66,66,
				197,197,197,197,197,197,197,197,197,197,197,197,197,197,197,197,197,197,200,177,66,66,66,66,66,66,66,66,66,66,66,66,
				197,197,197,197,221,218,218,218,218,218,218,218,218,218,220,197,197,197,197,200,177,66,66,66,66,66,66,66,66,66,66,66,
				218,218,218,218,66,66,66,66,66,66,66,66,66,66,217,218,220,197,197,197,198,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,220,197,197,198,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,196,197,197,198,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,196,197,197,198,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,196,197,197,198,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,196,197,197,198,66,66,66,66,66,66,66,66,66,66,66,
				66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,66,196,197,197,198,66,66,66,66,66,66,66,66,66,66,66
				};
		
	}

	@Override
	public void draw(Graphics g) {
		int tileColumn = 1;
		int tileRow = 1;
		int x = 0;
		int y = 0;
		for(int i = 1; i < tileArray.length+1; i++){
		    // Minus 1 so that the first tile draws at 0, 0
	        int pixelPosX = x + (tileColumn - 1) * tileSize;
	        int pixelPosY = y + (tileRow - 1) * tileSize;

	        drawTile(g, pixelPosX, pixelPosY, tileArray[i-1]);
	 
	        // Advance to the next tile
		    tileColumn += 1;
		    if(tileColumn > mapWidth)
		    {
		        tileColumn = 1;
		        tileRow += 1;
		    }
		}
	}
	
	public void drawTile(Graphics g, int x, int y, int gid){
		int sourceX = (((gid - 1) % sourceWidthInTiles) * tileSize);
		int sourceY = (Math.floorDiv(gid - 1, sourceWidthInTiles)) * tileSize;
		g.drawImage(mSource, x, y, x + tileSize, y + tileSize, sourceX, sourceY, sourceX + tileSize, sourceY + tileSize, null);
	}
		
}


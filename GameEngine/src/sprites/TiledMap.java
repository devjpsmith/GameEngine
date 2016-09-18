package sprites;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import core.IDrawable;


public class TiledMap implements IDrawable{
	protected int mapWidth = 32;			// how many tiles across the map is
	protected int tileSize = 32;			// the width of each tile
	protected int sourceWidthInTiles = 21;	// how many tiles across the source image is
	//protected int[] tileArray;				// the gid array from which our map will be built
	protected ArrayList<int[]> layerList;
	
	private int[] collidableTileArray = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,883,
			0,0,0,0,0,0,0,0,883,0,0,883,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,883,883,883,883,883,883,883,0,0,883,883,883,883,883,883,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,883,0,0,0,0,0,883,0,0,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			883,883,883,0,0,0,0,0,883,0,0,883,0,0,0,0,883,883,883,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,883,0,0,883,0,0,0,0,0,0,883,883,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,883,0,0,883,0,0,0,0,0,0,0,883,883,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,883,0,0,883,0,0,0,0,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,883,883,883,883,0,0,883,883,883,883,0,0,0,0,0,883,883,0,0,0,0,0,0,0,0,0,0,
			883,883,883,883,883,883,0,0,883,0,0,883,0,0,883,883,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,883,0,0,0,0,883,0,0,0,0,0,0,0,0,0,0};

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
		
		layerList = new ArrayList<int[]>();
		layerList.add(new int[]{44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,
				65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,
				44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,
				65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,
				44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,
				65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,
				44,45,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,
				65,66,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,
				52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,45,44,45,44,45,44,45,44,45,44,45,44,45,
				52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,65,66,65,66,65,66,65,66,65,66,65,66,
				52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,45,44,45,44,45,44,45,44,45,44,45,
				52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,66,65,66,65,66,65,66,65,66,65,66,
				52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,44,45,44,45,44,45,44,45,44,45,
				52,52,52,52,52,52,65,66,65,66,65,66,65,66,52,52,52,52,52,52,52,52,65,66,65,66,65,66,65,66,65,66,
				44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,52,52,52,52,52,52,44,45,44,45,44,45,44,45,44,45,
				65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,52,52,52,52,52,52,65,66,65,66,65,66,65,66,65,66,
				44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,52,52,52,52,52,52,44,45,44,45,44,45,44,45,44,45,
				65,66,65,66,65,66,65,66,65,66,65,66,65,66,65,66,52,52,52,52,52,52,65,66,65,66,65,66,65,66,65,66,
				44,45,44,45,44,45,44,45,44,45,44,45,44,45,44,45,52,52,52,52,52,52,44,45,44,45,44,45,44,45,44,45});
		
		layerList.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,112,34,108,109,345,346,347,348,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,112,34,129,130,366,367,368,369,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,112,34,108,109,387,388,389,390,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,112,34,129,130,408,409,410,411,
				0,0,0,0,0,0,0,0,0,176,176,0,0,0,0,0,0,0,0,0,0,0,0,0,112,55,17,108,109,430,431,432,
				0,0,0,0,0,0,0,0,0,215,215,0,0,0,0,0,0,0,0,0,0,0,0,0,133,116,55,56,17,109,108,109,
				0,0,7,8,8,8,8,8,8,215,215,8,8,8,8,8,9,0,0,0,0,0,0,0,0,133,134,116,55,17,129,130,
				0,0,28,0,0,0,0,0,0,215,215,0,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,133,116,55,56,56,
				8,8,32,0,0,0,0,0,0,215,215,0,0,0,0,0,31,8,9,0,0,0,0,0,0,0,0,0,133,134,134,134,
				0,0,0,0,0,0,0,0,0,215,215,0,0,0,0,0,0,0,31,9,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,215,215,0,0,0,0,0,0,0,0,31,9,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,215,215,0,0,0,0,0,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,10,50,50,50,215,215,50,50,50,11,0,0,0,0,0,31,9,0,0,0,0,0,0,0,0,0,0,
				50,50,50,50,50,51,0,0,0,215,215,0,0,0,49,50,11,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,218,218,0,0,0,0,0,28,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,28,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,28,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,28,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,28,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0});

		layerList.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,193,0,0,195,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,214,0,0,216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,235,0,0,237,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});		

		
	}

	private int getGidFromPoint(Point p){
		int row = Math.floorDiv((int)p.getY(), tileSize); 
		int col = Math.floorDiv((int)p.getX(), tileSize) + 1; 
		return row * sourceWidthInTiles + col; 
	}
	
	public void setSpriteBlocked(Sprite sprite){
		Point a = new Point(), b = new Point(), c = new Point(), d = new Point();
		a.setLocation(sprite.getX(), sprite.getY());
		b.setLocation(sprite.getX() + sprite.getWidth(), sprite.getY());
		c.setLocation(sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
		d.setLocation(sprite.getX(), sprite.getY() + sprite.getHeight());
		
	}
	
	@Override
	public void draw(Graphics g) {
		for (Iterator<int[]> i = layerList.iterator(); i.hasNext();){
			int[] tileArray = i.next();
			
			int tileColumn = 1;
			int tileRow = 1;
			int x = 0;
			int y = 0;
			for(int j = 1; j < tileArray.length+1; j++){
			    // Minus 1 so that the first tile draws at 0, 0
		        int pixelPosX = x + (tileColumn - 1) * tileSize;
		        int pixelPosY = y + (tileRow - 1) * tileSize;

		        drawTile(g, pixelPosX, pixelPosY, tileArray[j - 1]);
		 
		        // Advance to the next tile
			    tileColumn += 1;
			    if(tileColumn > mapWidth)
			    {
			        tileColumn = 1;
			        tileRow += 1;
			    }
			}
		}
		
	}
	
	public void drawTile(Graphics g, int x, int y, int gid){
		int sourceX = (((gid - 1) % sourceWidthInTiles) * tileSize);
		int sourceY = (Math.floorDiv(gid - 1, sourceWidthInTiles)) * tileSize;
		g.drawImage(mSource, x, y, x + tileSize, y + tileSize, sourceX, sourceY, sourceX + tileSize, sourceY + tileSize, null);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
		
}


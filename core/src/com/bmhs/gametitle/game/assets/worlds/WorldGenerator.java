package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];


        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        System.out.println(mapSeed.y + " " + mapSeed.x);

        worldIntMap[(int)mapSeed.y][(int)mapSeed.x] = 4;

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++) {
                Vector2 tempVectore = new Vector2(c,r);
                if(tempVectore.dst(mapSeed) < 10) {
                    worldIntMap[r][c] = 2;
                }

            }
        }

        //call methods to build 2D array
        //randomize();
        setWater();
        setSeed();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
        generateWorldTextFile();
    }

    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    /*public void leftCoast() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                if(c <10) {
                    worldIntMap[r][c] = 3;
                }
            }
        }
    }

     */



    public void setSeed() {
        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        System.out.println(mapSeed.y + " " + mapSeed.x);

        worldIntMap[(int)mapSeed.y][(int)mapSeed.x] = 1;

        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++) {
                Vector2 tempVectore = new Vector2(c,r);
                if(tempVectore.dst(mapSeed) <= 0) {
                    worldIntMap[r][c] = 24 ;
                }
            }
        }
    }


    public void setWater() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++){
                if(c >= 0) {
                    worldIntMap[r][c] = 27;
                }
            }
        }
    }


    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

    private void generateWorldTextFile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }



}

package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;

import java.util.Random;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    private int seedColor, lightGreen, Green;

    public WorldGenerator(int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        seedColor = 21;
        lightGreen = 23;




      // Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        //System.out.println(mapSeed.y + " " + mapSeed.x);

      //  worldIntMap[(int) mapSeed.y][(int) mapSeed.x] = 4;



        //call methods to build 2D array
        //randomize();





        setWater();
        createClusters(5, 10);
        seedIslands(9);
        searchAndExpand(100, seedColor, 27, .3);
        searchAndExpand(60, seedColor, 26, .4);
        searchAndExpand(55, seedColor, 25, .5 );
        searchAndExpand(35, seedColor, 24, .7 );
       searchAndExpand(24, seedColor, 23, .75 );
       searchAndExpand(16, seedColor,  22, .85 );





        //setSeed();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");

        generateWorldTextFile();
    }


    private void createClusters(int clusters, int clusterSize) {
        Random random = new Random();

        for (int i = 0; i < clusters; i++) {
            int rSeed = random.nextInt(worldIntMap.length);
            int cSeed = random.nextInt(worldIntMap[0].length);
            worldIntMap[rSeed][cSeed] = seedColor;
            for (int x = 0; x < clusterSize; x++) {
                int newRow = rSeed + random.nextInt(14)-1;
                int newCol = cSeed +  random.nextInt(6)-1;
                if (newRow >= 0 && newRow < worldIntMap.length && newCol >= 0 && newCol < worldIntMap[0].length &&
                        worldIntMap[newRow][newCol] != seedColor) {
                    worldIntMap[newRow][newCol] = seedColor;
                }
            }
        }
    }

    private void seedIslands(int num) {
        for (int i = 0; i < num; i++) {
            int rSeed = MathUtils.random(worldIntMap.length - 1);
            int cSeed = MathUtils.random(worldIntMap[0].length - 1);
            worldIntMap[rSeed][cSeed] = seedColor;
        }
    }




    private void searchAndExpand(int radius, int numToFind, int numToWrite, double probability) {

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == numToFind) {
                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {
                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != numToFind) {
                                double rowR = subRow - r;
                                double ColC = subCol - c;
                                double rowAndColumnSqrd = (rowR*rowR) + (ColC*ColC);
                                double reworkedProbability = probability * (rowAndColumnSqrd/radius);
                                if(Math.random() > reworkedProbability) {
                                    worldIntMap[subRow][subCol] = numToWrite;

                                }
                            }

                        }
                    }

                }
            }
        }
    }





    private void searchAndExpand(int radius) {
        for(int r = 0; r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++){
                if(worldIntMap[r][c] == seedColor) {

                    for(int subRow = r-radius; subRow <= r+radius; subRow++){

                        for(int subCol = c-radius; subCol <= c+radius; subCol++){

                            if(subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length-1 && subCol <= worldIntMap[0].length-1 && worldIntMap[subRow][subCol] != seedColor) {
                               worldIntMap[subRow][subCol] = 2;
                            }

                        }
                }

            }
        }
    }
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


    public void setWater() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++){
                if(c >= 0) {
                    worldIntMap[r][c] = 28;
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

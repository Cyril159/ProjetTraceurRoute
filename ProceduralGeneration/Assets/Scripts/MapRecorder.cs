using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.IO;

public static class MapRecorder
{
    public static void SaveMapAsTXT(float[,] heightMap, string savedFileName, int waterlevel)
    {
        if (savedFileName == "")
        {
            savedFileName = "save";
        }

        int width = heightMap.GetLength(0);
        int height = heightMap.GetLength(1);

        string line;

        string docPath = "Ressources";

        using (StreamWriter outputFile = new StreamWriter(Path.Combine(docPath, savedFileName + ".txt")))
        {
            outputFile.WriteLine(width.ToString() + " " + height.ToString() + " " + (waterlevel/20f).ToString());

            for (int y = 0; y < height; y++)
            {
                line = heightMap[0,y].ToString();

                for (int x = 1; x < width; x++)
                {
                    line += " " + heightMap[x,y];
                }

                outputFile.WriteLine(line);
            }
        }
    }
}

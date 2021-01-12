using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.IO;

public static class MapLoader
{
    public static float[,] LoadMapTXT(string folder)
    {
        string path = "Ressources/";
        string file = path + folder + ".txt";

        if (! System.IO.File.Exists(file))
        {
            System.Console.WriteLine("Can't find the file " + folder + ".txt");
            throw new FileNotFoundException("Can't find the file " + folder + ".txt");
        }

        string text = System.IO.File.ReadAllText(file);

        char[] charSeparators = new char[] {' ', '\n'};

        string[] lines = text.Split(charSeparators, StringSplitOptions.None);

        int width = int.Parse(lines[0]);
        int height = int.Parse(lines[1]);
        float waterLevel = float.Parse(lines[2]);

        

        float[,] heightMap = new float[width, height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                heightMap[j, i] = float.Parse(lines[i * width + j + 3]);
            }
        }

        return heightMap;
    }

}

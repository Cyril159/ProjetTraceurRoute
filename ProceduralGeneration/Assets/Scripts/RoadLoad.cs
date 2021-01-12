using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.IO;

public static class RoadLoad
{
    public static int[,] LoadPoints(string nameFile)
    {
        string path = "Ressources/";
        string file = path + nameFile + ".txt";

        if (! System.IO.File.Exists(file))
        {
            System.Console.WriteLine("Can't find the file " + nameFile + ".txt");
            throw new FileNotFoundException("Can't find the file " + nameFile + ".txt");
        }

        string text = System.IO.File.ReadAllText(file);

        char[] charSeparators = new char[] {',', '\n'};

        string[] lines = text.Split(charSeparators, StringSplitOptions.None);

        int size = int.Parse(lines[0]);

        int [,] tabPoints = new int[2, size];

        for (int i = 0; i < size; i++)
        {
            tabPoints[0, i] = int.Parse(lines[i * 2 + 0 + 1]);
            tabPoints[1, i] = int.Parse(lines[i * 2 + 1 + 1]);
        }

        return tabPoints;
    }


    public static Color[] DrawLine(int x0, int y0, int x1, int y1, Color[] colourMap, int pixWidth)
    {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int y;
        for (int x = x0; x <= x1; x++)
        {
            y = y1 + dy * (x - x1) / dx;
            colourMap[y*pixWidth + x] = new Color(20f,0f,0f);
        }

        return colourMap;
    }

    public static Color[] DrawRoad(int[,] pointsOfRoad, Color[] colourMap, int pixWidth)
    {
        int size = pointsOfRoad.GetLength(1);

        for (int i = 0; i < size-1; i++)
        {
            if (pointsOfRoad[0,i] >= pointsOfRoad[0,i+1])
            {
                colourMap = DrawLine(pointsOfRoad[0,i+1],pointsOfRoad[1,i+1],pointsOfRoad[0,i],pointsOfRoad[1,i],colourMap,pixWidth);
                colourMap = DrawLine(pointsOfRoad[0,i+1],pointsOfRoad[1,i+1]+1,pointsOfRoad[0,i],pointsOfRoad[1,i]+1,colourMap,pixWidth);
                colourMap = DrawLine(pointsOfRoad[0,i+1]-1,pointsOfRoad[1,i+1],pointsOfRoad[0,i]-1,pointsOfRoad[1,i],colourMap,pixWidth);
                colourMap = DrawLine(pointsOfRoad[0,i+1]-1,pointsOfRoad[1,i+1]+1,pointsOfRoad[0,i]-1,pointsOfRoad[1,i]+1,colourMap,pixWidth);
            }
            else
            {
                colourMap = DrawLine(pointsOfRoad[0,i],pointsOfRoad[1,i],pointsOfRoad[0,i+1],pointsOfRoad[1,i+1],colourMap,pixWidth);
                colourMap = DrawLine(pointsOfRoad[0,i],pointsOfRoad[1,i]+1,pointsOfRoad[0,i+1],pointsOfRoad[1,i+1]+1,colourMap,pixWidth);
                colourMap = DrawLine(pointsOfRoad[0,i]-1,pointsOfRoad[1,i],pointsOfRoad[0,i+1]-1,pointsOfRoad[1,i+1],colourMap,pixWidth);
                colourMap = DrawLine(pointsOfRoad[0,i]-1,pointsOfRoad[1,i]+1,pointsOfRoad[0,i+1]-1,pointsOfRoad[1,i+1]+1,colourMap,pixWidth);
            }
        }

        return colourMap;
    }
}

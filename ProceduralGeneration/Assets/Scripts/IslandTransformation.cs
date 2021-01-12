﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public static class IslandTransformation
{
    public static float[,] islandTransformationWithEdges(float[,] noiseMapTex, int widthEdges)
    {
        double widthEdgesCoordd = System.Convert.ToDouble(widthEdges);

        for (int x = 0; x < noiseMapTex.GetLength(0); x++)
        {
            for (int y = 0; y < noiseMapTex.GetLength(1); y++)
            {
                double coeffx = 1.0d;

                if (x < widthEdges)
                {
                    coeffx = x/widthEdgesCoordd;
                }
                else if (x + widthEdges > noiseMapTex.GetLength(0))
                {
                    coeffx = (noiseMapTex.GetLength(0) - x + 1)/widthEdgesCoordd;
                }

                double coeffy = 1.0d;

                if (y < widthEdges)
                {
                    coeffy = y/widthEdgesCoordd;
                }
                else if (y + widthEdges > noiseMapTex.GetLength(0))
                {
                    coeffy = (noiseMapTex.GetLength(0) - y + 1)/widthEdgesCoordd;
                }

                double coeff = coeffx * coeffy;

                if (coeff < 1.0d)
                {
                    noiseMapTex[x, y] = System.Convert.ToSingle(noiseMapTex[x, y] *  Math.Sin(Math.Sqrt(coeff)*Math.PI/2));
                }
            }
        }

        return noiseMapTex;
    }


    private static double sqr(double value)
    {
        return value*value;
    }


    public static float[,] islandTransformationGaussian(float[,] noiseMapTex, double sigma1, double sigma2)
    {
        double x0 = noiseMapTex.GetLength(0) / 2.0d;
        double y0 = noiseMapTex.GetLength(1) / 2.0d;

        for (int x = 0; x < noiseMapTex.GetLength(0); x++)
        {
            for (int y = 0; y < noiseMapTex.GetLength(1); y++)
            {
                noiseMapTex[x,y] = System.Convert.ToSingle(noiseMapTex[x,y] * Math.Exp(-(sqr(x - x0) / (2 * sqr(sigma1)) + sqr(y - y0) / (2 * sqr(sigma2)))));
            }
        }

        return noiseMapTex;
    }

    /*public static float[,] islandTransformation(float[,] noiseMapTex, int widthEdges, float sigma1, float sigma2,MapGenerator.TransformationIlsandMode transformationIlsandMode)
    {
        if (transformationIlsandMode == MapGenerator.TransformationIlsandMode.TransformEdges)
        {
            return islandTransformationWithEdges(noiseMapTex, widthEdges);
        }
        else if (transformationIlsandMode == MapGenerator.TransformationIlsandMode.TransformGaussian)
        {
            return islandTransformationGaussian(noiseMapTex, sigma1, sigma2);
        }
        else
        {
            return noiseMapTex;
        }
    }*/
}

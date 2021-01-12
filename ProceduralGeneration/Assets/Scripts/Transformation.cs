using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public static class Transformation
{
    private static double sqr(double value)
    {
        return value*value;
    }


    public static float[,] transformationGaussianInverse(float[,] noiseMapTex, double sigma1, double sigma2)
    {
        double x0 = noiseMapTex.GetLength(0) / 2.0d;
        double y0 = noiseMapTex.GetLength(1) / 2.0d;

        float maxNoiseHeight = float.MinValue;
        float minNoiseHeight = float.MaxValue;

        for (int x = 0; x < noiseMapTex.GetLength(0); x++)
        {
            for (int y = 0; y < noiseMapTex.GetLength(1); y++)
            {
                noiseMapTex[x,y] = System.Convert.ToSingle(noiseMapTex[x,y] * Math.Exp((sqr(x - x0) / (2*sqr(sigma1)) + sqr(y - y0) / (2*sqr(sigma2)))));

                if (noiseMapTex[x,y] > maxNoiseHeight)
                {
                    maxNoiseHeight = noiseMapTex[x,y];
                }
                else if (noiseMapTex[x,y] < minNoiseHeight)
                {
                    minNoiseHeight = noiseMapTex[x,y];
                }
            }
        }

        for (int y = 0; y < noiseMapTex.GetLength(1); y++)
        {
            for (int x = 0; x < noiseMapTex.GetLength(0); x++)
            {
                noiseMapTex[x, y] = Mathf.InverseLerp (minNoiseHeight, maxNoiseHeight, noiseMapTex[x, y]);
            }
        }
        

        return noiseMapTex;
    }

    public static float[,] transformationInverse(float[,] noiseMapTex)
    {
        double x0 = noiseMapTex.GetLength(0) / 2.0d;
        double y0 = noiseMapTex.GetLength(1) / 2.0d;

        float maxNoiseHeight = float.MinValue;
        float minNoiseHeight = float.MaxValue;

        for (int x = 0; x < noiseMapTex.GetLength(0); x++)
        {
            for (int y = 0; y < noiseMapTex.GetLength(1); y++)
            {
                noiseMapTex[x,y] = System.Convert.ToSingle(Math.Sqrt((noiseMapTex[x,y]-1)*(noiseMapTex[x,y]-1)));

                if (noiseMapTex[x,y] > maxNoiseHeight)
                {
                    maxNoiseHeight = noiseMapTex[x,y];
                }
                else if (noiseMapTex[x,y] < minNoiseHeight)
                {
                    minNoiseHeight = noiseMapTex[x,y];
                }
            }
        }

        for (int y = 0; y < noiseMapTex.GetLength(1); y++)
        {
            for (int x = 0; x < noiseMapTex.GetLength(0); x++)
            {
                noiseMapTex[x, y] = Mathf.InverseLerp (minNoiseHeight, maxNoiseHeight, noiseMapTex[x, y]);
            }
        }
        

        return noiseMapTex;
    }
}

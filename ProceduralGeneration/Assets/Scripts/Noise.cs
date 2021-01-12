using UnityEngine;
using System.Collections;
using System;

public static class Noise {

    public static float[,] GenerateNoiseMap(int pixWidth, int pixHeight, int seed, float scale, int octaves, float persistance, float lacunarity, Vector2 offset, MapGenerator.GeneratorMode generatorMode)
    {
        float[,] noiseMapTex = new float[pixWidth,pixHeight];

        System.Random prng = new System.Random (seed);

        // Eviter la limite de PerlinNoise()
        Vector2[] octaveOffsets = new Vector2[octaves];
        for (int i = 0; i < octaves; i++)
        {
            float offsetX = prng.Next (-100000, 100000) + offset.x;
            float offsetY = prng.Next (-100000, 100000) + offset.y;
            octaveOffsets [i] = new Vector2 (offsetX, offsetY);
        }

        // Vérification des valeurs des paramètres
        if (pixWidth < 1) { pixWidth = 1; }
        if (pixWidth > 1000) { pixWidth = 1000; }

        if (pixHeight < 1) { pixHeight = 1; }
        if (pixHeight > 1000) { pixHeight = 1000; }

        if (scale <= 0) { scale = 0.0001f; }

        if (lacunarity < 1) { lacunarity = 1; }

        if (octaves < 0) { octaves = 0; }


        float maxNoiseHeight = float.MinValue;
        float minNoiseHeight = float.MaxValue;

        // Le paramètre scale permet un "zoom" au centre de l'image
        float halfWidth = pixWidth / 2f;
        float halfHeight = pixHeight / 2f;

        // On génère les valeurs de la texture
        for (int y = 0; y < pixHeight; y++)
        {
            for (int x = 0; x < pixWidth; x++)
            {
                float amplitude = 1;
                float frequency = 1;

                float noiseHeight = 0;

                for (int i = 0; i < octaves; i++)
                {
                    float xCoord = (x-halfWidth) / scale * frequency + octaveOffsets[i].x;
                    float yCoord = (y-halfHeight) / scale * frequency + octaveOffsets[i].y;

                    double xCoordd = xCoord +0d;
                    double yCoordd = yCoord +0d;

                    float perlinValue;

                    if (generatorMode == MapGenerator.GeneratorMode.Standard)
                    {
                        perlinValue = Mathf.PerlinNoise(xCoord, yCoord)* 2 - 1;
                    }
                    else if (generatorMode == MapGenerator.GeneratorMode.Island)
                    {
                        perlinValue = System.Convert.ToSingle(System.Math.Cos(xCoordd)*System.Math.Cos(yCoordd)*System.Math.Cos(xCoordd)*System.Math.Cos(yCoordd));
                    }
                    else
                    {
                        perlinValue = System.Convert.ToSingle(System.Math.Cos(xCoordd)*System.Math.Cos(yCoordd));
                    }

                    noiseHeight += perlinValue * amplitude;

                    amplitude *= persistance;
                    frequency *= lacunarity;
                }

                // Garde une trace des valeurs minimale et maximale générées
                if (noiseHeight > maxNoiseHeight)
                {
                    maxNoiseHeight = noiseHeight;
                }
                else if (noiseHeight < minNoiseHeight)
                {
                    minNoiseHeight = noiseHeight;
                }


                noiseMapTex[x, y] = noiseHeight;
            }
        }

        // Normalisation sur l'intervalle [0,1]
        for (int y = 0; y < pixHeight; y++)
        {
            for (int x = 0; x < pixWidth; x++)
            {
                noiseMapTex[x, y] = Mathf.InverseLerp (minNoiseHeight, maxNoiseHeight, noiseMapTex[x, y]);
            }
        }
        
        return noiseMapTex;
    }

}

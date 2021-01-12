using UnityEngine;
using System.Collections;
using System;
using System.IO;

public class MapGenerator : MonoBehaviour
{
    // Modes d'affichage
    public enum DrawMode {NoiseMap, ColourMap, Mesh}
    public DrawMode drawMode;

    // Modes de génération
    public enum GeneratorMode {Standard, Lake, Island}
    public GeneratorMode generatorMode;


    [Header("Paramètres carte")]

    // Taille de la texture
    public int pixWidth;
    public int pixHeight;
    public bool autoUpdate;


    [Header("Paramètres génération")]

    [Range(1,10)]
    public int octaves;

    // Zoom
    public float scale;

    // Paramètres pour la génération de la texture
    [Range(0,1)]
    public float persistance;
    [Range(0,4)]
    public float lacunarity;

    // Déplacement sur la texture
    public Vector2 offset;

    // Graine
    public int seed;
    [Header("Édition")]
    // Niveau des eaux
    [Range(0,100)]
    public int waterLevel;

    // Paramètres de transformation en île 1
    public enum TransformationIlsandMode1 {None, TransformEdges}
    public TransformationIlsandMode1 transformationIlsandMode1;

    [Range(1,100)]
    public int widthEdges;


    public enum TransformationIlsandMode2 {None, TransformGaussienne}
    public TransformationIlsandMode2 transformationIlsandMode2;
    [Range(0,100)]
    public float sigma1;
    [Range(0,100)]
    public float sigma2;



    public enum TransformationMode3 {None, TransformationGaussianInverse}
    public TransformationMode3 transformationMode3;
    [Range(0,500)]
    public float alpha1;
    [Range(0,500)]
    public float alpha2;
    
    public enum TransformationInverse {None, Inverse}
    public TransformationInverse transformationInverse;



    [Header("Mailliage")]
    // Paramètres Mesh
    public float heightMultiplier;

    [Header("Chargement")]
    // Chargement d'un fichier
    public bool loadFolder;
    public string folderName;

    [Header("Sauvegarde")]
    // Enregistrement d'une carte
    public string savedFileName;

    [Header("Affichage route")]
    // Afficher la route
    public bool roadDraw;
    public string nameFileRoad;


    public float[,] BuildMap()
    {
        return Noise.GenerateNoiseMap(pixWidth, pixHeight, seed, scale, octaves, persistance, lacunarity, offset, generatorMode);
    }

    public float[,] LoadMap()
    {
        return MapLoader.LoadMapTXT(folderName);
    }

    public void GenerateMap()
    {
        float[,] noiseMapTex;

        if (loadFolder == true)
        {
            noiseMapTex = MapLoader.LoadMapTXT(folderName);
        }
        else
        {
            noiseMapTex = BuildMap();
        }



        if (transformationIlsandMode1 == TransformationIlsandMode1.TransformEdges)
        {
            IslandTransformation.islandTransformationWithEdges(noiseMapTex, widthEdges);
        }
        if (transformationIlsandMode2 == TransformationIlsandMode2.TransformGaussienne)
        {
            IslandTransformation.islandTransformationGaussian(noiseMapTex, sigma1, sigma2);
        }
        if (transformationMode3 == TransformationMode3.TransformationGaussianInverse)
        {
            Transformation.transformationGaussianInverse(noiseMapTex, alpha1, alpha2);
        }
        if (transformationInverse == TransformationInverse.Inverse)
        {
            Transformation.transformationInverse(noiseMapTex);
        }


        GradGenerator.TerrainType[] gradient = GradGenerator.GenerateGrad(waterLevel);

        Color[] colourMap = new Color[pixWidth*pixHeight];

        for (int y = 0; y < pixHeight; y++)
        {
            for (int x = 0; x < pixWidth; x++)
            {
                float currentHeight = noiseMapTex[x, y];

                for (int i = 0; i < gradient.Length; i++)
                {
                    if (currentHeight <= gradient[i].height)
                    {
                        colourMap [y*pixWidth + x] = gradient[i].colour;
                        break;
                    }
                }
            }
        }


        // Affichage de la route
        if (roadDraw == true)
        {
            int[,] points = RoadLoad.LoadPoints(nameFileRoad);
            colourMap = RoadLoad.DrawRoad(points,colourMap,pixWidth);
        }


        MapDisplay display = FindObjectOfType<MapDisplay> ();
        if (drawMode == DrawMode.NoiseMap)
        {
            display.DrawTexture(TextureGenerator.TextureFromHeightMap(noiseMapTex));
        }
        else if (drawMode == DrawMode.ColourMap)
        {
            display.DrawTexture(TextureGenerator.TextureFromColourMap(colourMap, pixWidth, pixHeight));
        }
        else if (drawMode == DrawMode.Mesh)
        {
            display.DrawMesh(MeshGenerator.GenerateTerrainMesh (noiseMapTex,heightMultiplier, waterLevel), TextureGenerator.TextureFromColourMap (colourMap, pixWidth, pixHeight));
        }
    }
    

    // Conditions de validité
    void OnValidate()
    {
        if (pixWidth < 1) {pixWidth = 1;}
        if (pixHeight < 1) {pixHeight = 1;}
        if (lacunarity < 1) {lacunarity = 1;}
        if (octaves < 0) {octaves = 0;}
    }

}
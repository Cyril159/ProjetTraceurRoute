using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.IO;

public static class GradGenerator
{
    // Ajout des couleurs
    [System.Serializable]
    public struct TerrainType
    {
        public float height;
        public Color colour;
    }


    public static TerrainType[] GenerateGrad(int waterLevel)
    {
        TerrainType[] grad = new TerrainType[100];

        for (int i = 0; i < 80; i++)
        {
            grad[i].height = (i+1)/100f;
            grad[i].colour = new Color(15/255f, (80+((150-80)/100)*(i))/255f, 17/255f);
        }


        for (int i = 80; i < 100; i++)
        {
            grad[i].height = (i+1)/100f;
            grad[i].colour = new Color((20+(255-20)/100*i)/255f,(20+(255-20)/100*i)/255f, (20+(255-20)/100*i)/255f);
        }

        for (int i = 0; i < waterLevel; i++)
        {
            grad[i].height = (i+1)/100f;
            grad[i].colour = new Color(32/255f, (80+((200-80)/waterLevel)*i)/255f, 229/255f);
        }
        
        return grad;
    }

}

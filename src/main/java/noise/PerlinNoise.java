package noise;

import java.util.Random;

public class PerlinNoise {

    private long seed;
    private Random rand;

    public PerlinNoise(long seed) {
        this.seed = seed;
        rand = new Random(seed);
    }

    public float[][] genWhiteNoiseMap(int mapWidth, int mapHeight) {
        float[][] noiseMap = new float[mapWidth][mapHeight];
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                noiseMap[x][y] = rand.nextFloat();
            }
        }
        return noiseMap;
    }

    public float[][] genSmoothNoiseMap(float[][] baseNoise, int octave) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][] noiseMap = new float[width][height];

        int samplePeriod = 1 << octave;
        float sampleFreq = 1.0f / (float)samplePeriod;

        for (int i = 0; i < width; i++) {
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width;
            float horizontalBlend = (i - sample_i0) * sampleFreq;

            for (int j = 0; j < height; j++) {
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height;
                float verticalBlend = (j - sample_j0) * sampleFreq;

                float top = interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontalBlend);

                float bottom = interpolate(baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1], horizontalBlend);

                noiseMap[i][j] = interpolate(top, bottom, verticalBlend);
            }
        }

        return noiseMap;
    }

    public float[][] genPerlinNoise(float[][] baseNoise, int octaveCount, float persistance, float amplitude) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][][] smoothNoise = new float[octaveCount][][];

        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = genSmoothNoiseMap(baseNoise, i);
        }

        float[][] perlinNoise = new float[width][height];
        float totalAmplitude = 0.0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    public static float interpolate(float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }
}

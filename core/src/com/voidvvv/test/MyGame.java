package com.voidvvv.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.graphics.glutils.IndexData;
import com.badlogic.gdx.graphics.glutils.VertexArray;
import com.badlogic.gdx.graphics.glutils.VertexData;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends Game {
    Model model;
    ModelBatch modelBatch;
    ModelInstance demo;
    PerspectiveCamera camera;
    float[] vertex = {
            // 第一个面
            0.f, 0f, 0f, 0, 0,
            0.f, 10f, 0f, 0, 1,
            10.f, 10f, 0f, 1, 1,

            10.f, 10f, 0f, 1, 1,
            10.f, 0.f, 0f, 1, 0,
            0.f, 0f, 0f, 0, 0,
            // 第二个面
            0, 10, 0, 0, 0,
            0, 10, 10, 0, 1,
            10, 10, 10, 1, 1,

            10, 10, 10, 1, 1,
            10, 10, 0, 1, 0,
            0, 10, 0, 0, 0,
            // 第三个面
            10, 0, 10, 0, 0,
            10, 10, 10, 0, 1,
            0, 10, 10, 1, 1,

            0, 10, 10, 1, 1,
            0, 0, 10, 1, 0,
            10, 0, 10, 0, 0,
            // 第四个面
            0.f, 0f, 0f, 0, 0,
            10, 0, 0, 0, 1,
            10, 0, 10, 1, 1,

            10, 0, 10, 1, 1,
            0, 0, 10, 1, 0,
            0.f, 0f, 0f, 0, 0,
            // 第五个面 需要注意，颜色渲染是在逆时针方向展示的，如果把这个面冲着立方体里面，那么外面看就是空白的
            10, 10, 10, 1, 1,
            10, 0, 10, 0, 1,
            10, 0, 0, 0, 0,


            10f, 0f, 0f, 0, 0,
            10, 10, 0, 1, 0,
            10, 10, 10, 1, 1,


            // 第六个面
            0, 0, 0, 0, 0,
            0, 0, 10, 0, 1,
            0, 10, 10, 1, 1,

            0, 10, 10, 1, 1,
            0, 10, 0, 1, 0,
            0, 0f, 0f, 0, 0,
    };

    Color[] colors = {
            Color.BLACK,
            Color.WHITE,
            Color.BLUE,
            Color.CHARTREUSE,
            Color.YELLOW,
            Color.FIREBRICK,
    };

    CameraInputController cameraInputController;

    @Override
    public void create() {


        System.out.println(vertex.length);
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        modelBatch = new ModelBatch();
        VertexAttribute positionAtt = VertexAttribute.Position();
//        VertexAttribute colorAtt = VertexAttribute.ColorUnpacked();
        VertexAttribute texCorrAtt = VertexAttribute.TexCoords(0);

        model = new Model();
        Mesh mesh = new Mesh(true, 36, 0, positionAtt, texCorrAtt);
        mesh.setVertices(vertex);
//        mesh.setIndices();
//        mesh.setInstanceData()

        Node n1 = new Node();

        for (int i = 0; i<6; i++){
            n1.parts.add(nodePart_(mesh, 6*i, colors[i]));
            System.out.println("i: " + i);
        }
        model.meshes.add(mesh);
        model.nodes.add(n1);

        demo = new ModelInstance(model);
//        demo.transform.scl(1.f).translate(0,0,0);

        camera.position.set(5,6,-20);
        camera.lookAt(5,5,5);
        cameraInputController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraInputController);
    }

    static NodePart nodePart_(Mesh mesh, int offset, Color color) {
        MeshPart mp = new MeshPart();
        mp.primitiveType = GL30.GL_TRIANGLES;
        mp.mesh = mesh;
        mp.offset = offset;
        mp.size = 6;

        NodePart np = new NodePart(mp, new Material(ColorAttribute.createDiffuse(color)));
        np.meshPart = mp;
        return np;
    }
    Vector3 tmp = new Vector3();
    @Override
    public void render() {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        camera.update();
        Gdx.gl.glClearColor(Color.GRAY.r,Color.GRAY.g,Color.GRAY.b,Color.GRAY.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraInputController.update();;

//        demo.transform.rotate(tmp.set(1,1,1),1.5f);

        modelBatch.begin(camera);
        modelBatch.render(demo);
        modelBatch.end();
    }
}

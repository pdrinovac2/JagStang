import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JagStang extends Applet {

    public static void main(String[] args) {
        new MainFrame(new JagStang(), 1330, 600);
    }

    TransformGroup spin = new TransformGroup();
    //PickCanvas pc;
    Appearance lit = new Appearance();

    public void init() {
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D cv = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(cv, BorderLayout.CENTER);
        SimpleUniverse su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        BranchGroup bg = createSceneGraph();
        bg.compile();
        su.addBranchGraph(bg);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);

        Transform3D tr = new Transform3D();
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        TransformGroup tg = new TransformGroup(tr);
        spin.addChild(tg);

        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        pa.setBackFaceNormalFlip(true);
        
        Appearance metal = new Appearance();
        Material ma = new Material();
        ma.setAmbientColor(new Color3f(0.30f, 0.30f, 0.35f ));
        ma.setDiffuseColor(new Color3f(0.30f, 0.30f, 0.50f ));
        ma.setSpecularColor(new Color3f(1f, 1f, 1f ));
        ma.setShininess(0.09f);
        metal.setMaterial(ma);
        metal.setPolygonAttributes(pa);
        
        Appearance white = new Appearance();
        Color3f whiteColor = new Color3f(255f / 255, 255f / 255, 255f / 255);
        white.setMaterial(new Material(whiteColor, whiteColor, whiteColor, whiteColor, 128f));
        white.setPolygonAttributes(pa);
        
        Appearance wood = new Appearance();
        Color3f woodColor = new Color3f(200f / 255, 165f / 255, 110f / 255);
        wood.setMaterial(new Material(woodColor, woodColor, woodColor, woodColor, 128f));
        wood.setPolygonAttributes(pa);
        
        Appearance darkwood = new Appearance();
        Color3f neckColor = new Color3f(130f / 255, 100f / 255, 85f / 255);
        darkwood.setMaterial(new Material(neckColor, neckColor, neckColor, neckColor, 128f));
        darkwood.setPolygonAttributes(pa);
        
        Appearance string = new Appearance();
        Color3f stringColor = new Color3f(192f / 255, 192f / 255, 192f / 255);
        string.setMaterial(new Material(stringColor, stringColor, stringColor, stringColor, 64f));
        string.setPolygonAttributes(pa);
        
        int xof, yof;

        GeneralPath headPath = new GeneralPath();
        headPath.moveTo(74, -81);
        headPath.quadTo(74, -60, 57, -60);
        headPath.lineTo(96, 55);
        headPath.moveTo(96, 49);
        headPath.quadTo(96, 69, 116, 69);
        headPath.quadTo(136, 69, 136, 49);
        headPath.quadTo(136, 29, 116, 29);
        headPath.moveTo(126, 31);
        headPath.quadTo(116, 29, 116, 19);
        headPath.quadTo(131, -11, 137, -52);
        headPath.quadTo(109, -52, 109, -81);
        headPath.lineTo(74, -81);
        Shape3D head = new ExtrudeShape(headPath, 8.075f);
        head.setAppearance(wood);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), (26.24375f - (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(head);

        addPegs(metal);
        
        GeneralPath bottomNeckPath = new GeneralPath();
        bottomNeckPath.moveTo(74, -81);
        bottomNeckPath.lineTo(109, -81);
        bottomNeckPath.lineTo(109, -358);
        bottomNeckPath.lineTo(74, -358);
        Shape3D bottomNeck = new ExtrudeShape(bottomNeckPath, 7 * 10.09375f / 8);
        bottomNeck.setAppearance(wood);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), (26.24375f - (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(bottomNeck);

        GeneralPath topNeckPath = new GeneralPath();
        topNeckPath.moveTo(74, -81);
        topNeckPath.lineTo(109, -81);
        topNeckPath.lineTo(109, -380);
        topNeckPath.quadTo(91, -390, 74, -380);
        Shape3D topNeck = new ExtrudeShape(topNeckPath, 10.09375f / 8);
        topNeck.setAppearance(darkwood);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(topNeck);

        Appearance fretBoardTex = new Appearance();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("resources\\fretboard.png"));
        } catch (IOException ex) {
            Logger.getLogger(JagStang.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextureLoader loader = new TextureLoader(img);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        texture.setEnable(true);
        fretBoardTex.setTexture(texture);
        fretBoardTex.setPolygonAttributes(pa);
        Box f = new Box(35f, 295f, 0f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, fretBoardTex);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-72f * (1f / 400)), (-8f * (1f / 400)), (28.24375f * (1f / 400))));
        tr.setScale(1f / 800);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(f);

        GeneralPath fretBoardPath = new GeneralPath();
        fretBoardPath.moveTo(74, -375);
        fretBoardPath.lineTo(109, -375);
        fretBoardPath.quadTo(91, -385, 74, -375);
        Shape3D fretBoard = new ExtrudeShape(fretBoardPath, 0);
        fretBoard.setAppearance(fretBoardTex);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), (28.24375f * (1f / 400))));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(fretBoard);

        GeneralPath bodyPath = new GeneralPath();
        bodyPath.moveTo(17, -335);
        bodyPath.quadTo(63, -374, 74, -358);
        bodyPath.lineTo(109, -358);
        bodyPath.quadTo(112, -405, 149, -383);
        bodyPath.quadTo(156, -377, 163, -387);
        bodyPath.quadTo(172, -405, 163, -456);
        bodyPath.quadTo(154, -477, 172, -510);
        bodyPath.quadTo(186, -542, 190, -576);
        bodyPath.quadTo(184, -624, 173, -635);
        bodyPath.quadTo(160, -650, 120, -645);
        bodyPath.quadTo(65, -640, 10, -565);
        bodyPath.quadTo(-1, -540, 7, -518);
        bodyPath.quadTo(25, -479, 32, -453);
        bodyPath.quadTo(5, -360, 13, -335);
        bodyPath.quadTo(15, -332, 17, -335);
        Shape3D body = new ExtrudeShape(bodyPath, 26.24375f);
        Color3f bodyColor = new Color3f(160f / 255, 220f / 255, 212f / 255);
        Appearance paint = new Appearance();
        paint.setMaterial(new Material(bodyColor, bodyColor, bodyColor, bodyColor, 128f));
        paint.setPolygonAttributes(pa);
        body.setAppearance(paint);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), 0f));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(body);

        GeneralPath pickGuardPath = new GeneralPath();
        xof = -5; yof = -10;
        pickGuardPath.moveTo(67 + xof, -370 + yof);
        pickGuardPath.quadTo(75 + xof, -365 + yof, 82 + xof, -370 + yof);
        pickGuardPath.quadTo(104 + xof, -372 + yof, 114 + xof, -370 + yof);
        pickGuardPath.quadTo(126 + xof, -410 + yof, 158 + xof, -380 + yof);
        pickGuardPath.quadTo(163 + xof, -407 + yof, 160 + xof, -432 + yof);
        pickGuardPath.quadTo(157 + xof, -450 + yof, 154 + xof, -460 + yof);
        pickGuardPath.quadTo(151 + xof, -478 + yof, 153 + xof, -489 + yof);
        pickGuardPath.quadTo(147 + xof, -494 + yof, 137 + xof, -496 + yof);
        pickGuardPath.quadTo(133 + xof, -503 + yof, 125 + xof, -508 + yof);
        pickGuardPath.quadTo(95 + xof, -515 + yof, 60 + xof, -510 + yof);
        pickGuardPath.quadTo(49 + xof, -488 + yof, 48 + xof, -464 + yof);
        pickGuardPath.quadTo(47 + xof, -449 + yof, 49 + xof, -432 + yof);
        pickGuardPath.quadTo(56 + xof, -406 + yof, 67 + xof, -370 + yof);
        Shape3D pickGuard = new ExtrudeShape(pickGuardPath, 10.09375f / 16);
        Color3f pickGuardColor = new Color3f(220f / 255, 220f / 255, 230f / 255);
        Appearance marble = new Appearance();
        marble.setMaterial(new Material(pickGuardColor, pickGuardColor, pickGuardColor, pickGuardColor, 128f));
        marble.setPolygonAttributes(pa);
        pickGuard.setAppearance(marble);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(pickGuard);
        pickGuard = new ExtrudeShape(pickGuardPath, 10.09375f / 18);
        Color3f blackColor = new Color3f(0f / 255, 0f / 255, 0f / 255);
        Appearance black = new Appearance();
        black.setMaterial(new Material(blackColor, blackColor, blackColor, blackColor, 128f));
        black.setPolygonAttributes(pa);
        pickGuard.setAppearance(black);
        tr = new Transform3D();
        tr.setScale(1f / 398);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-302f * (1f / 400), -100.5f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(pickGuard);
        
        Cylinder knob1 = new Cylinder(14f, 15f, Primitive.GENERATE_NORMALS, black);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((215f * (1f / 400)), (40f * (1f / 400)), (30f * (1f / 400))));
        tr.setScale(1f / 800);
        tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(knob1);
        
        Cylinder knob2 = new Cylinder(14f, 15f, Primitive.GENERATE_NORMALS, black);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((240f * (1f / 400)), (55f * (1f / 400)), (30f * (1f / 400))));
        tr.setScale(1f / 800);
        tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(knob2);
        
        GeneralPath humbuckPath = new GeneralPath();
        xof = -5; yof = 10;
        humbuckPath.moveTo(76f + xof, -417f + yof);
        humbuckPath.lineTo(115f + xof, -424f + yof);
        humbuckPath.quadTo(117f + xof, -429f + yof, 113f + xof, -434f + yof);
        humbuckPath.lineTo(74f + xof, -427f + yof);
        humbuckPath.quadTo(72f + xof, -422f + yof, 76f + xof, -417f + yof);
        
        Shape3D hum1 = new ExtrudeShape(humbuckPath, 5f);
        hum1.setAppearance(white);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(hum1);
        addCircles(0, metal);
        
        Shape3D hum2 = new ExtrudeShape(humbuckPath, 5f);
        hum2.setAppearance(white);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-250f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(hum2);
        addCircles(50, metal);
        
        Shape3D hum3 = new ExtrudeShape(humbuckPath, 5f);
        hum3.setAppearance(white);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-240f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(hum3);
        addCircles(60, metal);
        
        GeneralPath metal1Path = new GeneralPath();
        xof = -5; yof = -10;
        metal1Path.moveTo(151 + xof, -478 + yof);
        metal1Path.quadTo(158 + xof, -503 + yof, 162 + xof, -511 + yof);
        metal1Path.quadTo(170 + xof, -525 + yof, 178 + xof, -540 + yof);
        metal1Path.quadTo(180 + xof, -553 + yof, 179 + xof, -560 + yof);
        metal1Path.quadTo(177 + xof, -564 + yof, 171 + xof, -567 + yof);
        metal1Path.quadTo(164 + xof, -562 + yof, 151 + xof, -540 + yof);
        metal1Path.quadTo(142 + xof, -527 + yof, 126 + xof, -509 + yof);
        metal1Path.quadTo(133 + xof, -494 + yof, 151 + xof, -478 + yof);
        Shape3D metal1 = new ExtrudeShape(metal1Path, 10.09375f / 17);
        metal1.setAppearance(metal);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(metal1);
        
        GeneralPath metal2Path = new GeneralPath();
        xof = -5; yof = -10;
        metal2Path.moveTo(105f + xof, -493f + yof);
        metal2Path.quadTo(126f + xof, -493f + yof, 126f + xof, -509f + yof);
        metal2Path.lineTo(131f + xof, -540f + yof);
        metal2Path.quadTo(131f + xof, -556f + yof, 120f + xof, -561f + yof);
        metal2Path.quadTo(97f + xof, -566f + yof, 74f + xof, -561f + yof);
        metal2Path.quadTo(63f + xof, -556f + yof, 63f + xof, -540f + yof);
        metal2Path.lineTo(67f + xof, -509f + yof);
        metal2Path.quadTo(67f + xof, -493f + yof, 78f + xof, -493f + yof);
        metal2Path.lineTo(105f + xof, -493f + yof);
        Shape3D metal2 = new ExtrudeShape(metal2Path, 10.09375f / 15);
        metal2.setAppearance(metal);
        tr = new Transform3D();
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tr.setTranslation(new Vector3f(-300f * (1f / 400), -100f * (1f / 400), 26.24375f * (1f / 400)));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(metal2);
        
        Cylinder m = new Cylinder(12f, 130f, Primitive.GENERATE_NORMALS, metal);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((255f * (1f / 400)), (-8f * (1f / 400)), (35f * (1f / 400))));
        tr.setScale(1f / 800);
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(m);
        
        Cylinder EtoPeg = new Cylinder(1.5f, 32f, Primitive.GENERATE_NORMALS, string);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-235f) * (1f / 400), (-23f + 0 * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(EtoPeg);
        
        Cylinder AtoPeg = new Cylinder(1.25f, 48f, Primitive.GENERATE_NORMALS, string);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-243f) * (1f / 400), (-23f + 1 * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(AtoPeg);
        
        Cylinder DtoPeg = new Cylinder(1.0f, 70f, Primitive.GENERATE_NORMALS, string);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-254f) * (1f / 400), (-23f + 2 * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(DtoPeg);
        
        Cylinder GtoPeg = new Cylinder(0.75f, 90f, Primitive.GENERATE_NORMALS, string);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-264f) * (1f / 400), (-23f + 3 * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(GtoPeg);
        
        Cylinder BtoPeg = new Cylinder(0.5f, 110f, Primitive.GENERATE_NORMALS, string);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-274f) * (1f / 400), (-23f + 4 * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(BtoPeg);
        
        Cylinder etoPeg = new Cylinder(0.25f, 130f, Primitive.GENERATE_NORMALS, string);
        tr = new Transform3D();
        tr.setTranslation(new Vector3f((-284f) * (1f / 400), (-23f + 5 * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
        tr.setScale(1f / 400);
        tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
        tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(etoPeg);
        
        Cylinder[] EString = makeString(1.5f, 0, string);
        Cylinder[] AString = makeString(1.25f, 1, string);
        Cylinder[] DString = makeString(1f, 2, string);
        Cylinder[] GString = makeString(0.75f, 3, string);
        Cylinder[] BString = makeString(0.5f, 4, string);
        Cylinder[] eString = makeString(0.25f, 5, string);
        
        MouseRotate rotator = new MouseRotate(spin);
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        spin.addChild(rotator);

        MouseTranslate translator = new MouseTranslate(spin);
        translator.setSchedulingBounds(bounds);
        spin.addChild(translator);

        MouseZoom zoom = new MouseZoom(spin);
        zoom.setSchedulingBounds(bounds);
        spin.addChild(zoom);

        Background background = new Background(0.5f, 0.5f, 0.5f);
        background.setApplicationBounds(bounds);
        root.addChild(background);
        DirectionalLight light = new DirectionalLight(new Color3f(Color.white),
                new Vector3f(1f, -10f, -1f));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        return root;
    }

    public void addCircles(float offset, Appearance ap) {
        Transform3D tr = new Transform3D();
        TransformGroup tg = new TransformGroup(tr);
        
        float xof = 1, yof = 6;
        for (int i = 0; i < 6; i++) {
            Cylinder c = new Cylinder(4f, 0f, Primitive.GENERATE_NORMALS, ap);
            tr = new Transform3D();
            tr.setTranslation(new Vector3f(((112.5f + offset + xof * i) * (1f / 400)), ((-25f + yof * i) * (1f / 400)), 32.24375f * (1f / 400)));
            tr.setScale(1f / 800);
            tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
            tg = new TransformGroup(tr);
            spin.addChild(tg);
            tg.addChild(c);
        }
    }
    
    public void addPegs(Appearance ap) {
        for (int i = 0; i < 6; i++) {
            Transform3D tr = new Transform3D();
            TransformGroup tg = new TransformGroup(tr);
            Cylinder c = new Cylinder(9f, 1f, Primitive.GENERATE_NORMALS, ap);
            tr = new Transform3D();
            tr.setTranslation(new Vector3f(((-250f - 20f * i) * (1f / 400)), ((-25f + 6.5f * i)  * (1f / 400)), (35f - (7 * 10.09375f / 8)) * (1f / 400)));
            tr.setScale(1f / 800);
            tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
            tg = new TransformGroup(tr);
            spin.addChild(tg);
            tg.addChild(c);
            
            Cylinder p = new Cylinder(6f, 25f, Primitive.GENERATE_NORMALS, ap);
            tr = new Transform3D();
            tr.setTranslation(new Vector3f(((-250f - 20f * i) * (1f / 400)), ((-25f + 6.5f * i)  * (1f / 400)), (38.24375f - (7 * 10.09375f / 8)) * (1f / 400)));
            tr.setScale(1f / 800);
            tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
            tg = new TransformGroup(tr);
            spin.addChild(tg);
            tg.addChild(p);
            
            Box b = new Box(12f, 10f, 8f, Primitive.GENERATE_NORMALS, ap);
            tr = new Transform3D();
            tr.setTranslation(new Vector3f(((-250f - 20f * i) * (1f / 400)), ((-28f + 6.5f * i)  * (1f / 400)), (24.24375f - (7 * 10.09375f / 8)) * (1f / 400)));
            tr.setScale(1f / 800);
            tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
            tg = new TransformGroup(tr);
            spin.addChild(tg);
            tg.addChild(b);
            
            GeneralPath pegPath = new GeneralPath();
            pegPath.moveTo(74, -71);
            pegPath.lineTo(54, -91);
            pegPath.quadTo(44, -81, 34, -91);
            pegPath.quadTo(24, -101, 39, -116);
            pegPath.quadTo(54, -131, 64, -121);
            pegPath.quadTo(74, -111, 64, -101);
            pegPath.lineTo(84, -81);
            pegPath.lineTo(74, -71);
            Shape3D peg = new ExtrudeShape(pegPath, 10f);
            peg.setAppearance(ap);
            tr = new Transform3D();
            tr.setTranslation(new Vector3f(((-250f - 40f - 20f * i) * (1f / 400)), ((-28f + 37f + 6.5f * i)  * (1f / 400)), (20.24375f - (7 * 10.09375f / 8)) * (1f / 400)));
            tr.setScale(1f / 840);
            tg = new TransformGroup(tr);
            spin.addChild(tg);
            tg.addChild(peg);
        }
    }
    
    public Cylinder[] makeString(float radius, int num, Appearance ap) {
        Transform3D tr = new Transform3D();
        TransformGroup tg = new TransformGroup(tr);
        Cylinder[] returnArray = new Cylinder[23];
        
        float inc = 0;
        float offset = 0;
        for (int i = 0; i < 23; i++) {
            tr = new Transform3D();
            tr.setScale(1f / 400);
            tr.setRotation(new AxisAngle4d(0, 0, 1, Math.PI / 2));
            inc = 0;
            if (i == 0) inc = 23;
            else if (i >= 1 && i <= 2) inc = 22;
            else if (i >= 3 && i <= 4) inc = 19;
            else if (i >= 5 && i <= 6) inc = 17;
            else if (i == 7) inc = 15;
            else if (i >= 8 && i <= 10) inc = 14;
            else if (i >= 11 && i <= 14) inc = 12;
            else if (i >= 15 && i <= 18) inc = 9;
            else if (i == 19) inc = 8;
            else if (i >= 20 && i <= 21) inc = 7;
            else {
                inc = 175;
                offset -= inc / 2;
                offset += 3;
            }
            offset += inc;
            
            Cylinder c = new Cylinder(radius, inc);
            //PickTool.setCapabilities(c, PickTool.INTERSECT_TEST);
            //c.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
            c.setAppearance(ap);
            tr.setTranslation(new Vector3f((offset - 231f) * (1f / 400), (-23f + num * 6) * (1f / 400), (26.24375f + (7 * 10.09375f / 8)) * (1f / 400)));
            tg = new TransformGroup(tr);
            spin.addChild(tg);
            tg.addChild(c);
            returnArray[i] = c;
        }
        return returnArray;
    }

    class ExtrudeShape extends Shape3D {

        public ExtrudeShape(GeneralPath path, float depth) {
            PathIterator iter = path.getPathIterator(new AffineTransform());
            Vector ptsVector = new Vector();
            float[] seg = new float[6];
            float x = 0, y = 0;
            float x0 = 0, y0 = 0;
            while (!iter.isDone()) {
                int segType = iter.currentSegment(seg);
                switch (segType) {
                    case PathIterator.SEG_MOVETO:
                        x = x0 = seg[0];
                        y = y0 = seg[1];
                        ptsVector.add(new Point3f(x, y, 0));
                        ptsVector.add(new Point3f(x, y, 0));
                        break;
                    case PathIterator.SEG_LINETO:
                        x = seg[0];
                        y = seg[1];
                        ptsVector.add(new Point3f(x, y, 0));
                        ptsVector.add(new Point3f(x, y, 0));
                        break;
                    case PathIterator.SEG_QUADTO:
                        for (int i = 1; i < 10; i++) {
                        float t = (float) i / 10f;
                        float xi = (1 - t) * (1 - t) * x + 2 * t * (1 - t) * seg[0] + t * t * seg[2];
                        float yi = (1 - t) * (1 - t) * y + 2 * t * (1 - t) * seg[1] + t * t * seg[3];
                        ptsVector.add(new Point3f(xi, yi, 0));
                        ptsVector.add(new Point3f(xi, yi, 0));
                    }
                        x = seg[2];
                        y = seg[3];
                        ptsVector.add(new Point3f(x, y, 0));
                        ptsVector.add(new Point3f(x, y, 0));
                        break;
                    case PathIterator.SEG_CUBICTO:
                        for (int i = 1; i < 20; i++) {
                        float t = (float) i / 20f;
                        float xi = (1 - t) * (1 - t) * (1 - t) * x + 3 * t * (1 - t) * (1 - t) * seg[0]
                                + 3 * t * t * (1 - t) * seg[2] + t * t * t * seg[4];
                        float yi = (1 - t) * (1 - t) * (1 - t) * y + 3 * t * (1 - t) * (1 - t) * seg[1]
                                + 3 * t * t * (1 - t) * seg[3] + t * t * t * seg[5];
                        ptsVector.add(new Point3f(xi, yi, 0));
                        ptsVector.add(new Point3f(xi, yi, 0));
                    }
                        x = seg[2];
                        y = seg[3];
                        ptsVector.add(new Point3f(x, y, 0));
                        ptsVector.add(new Point3f(x, y, 0));
                        break;
                    case PathIterator.SEG_CLOSE:
                        x = x0;
                        y = y0;
                        ptsVector.add(new Point3f(x, y, 0));
                        break;
                }
                iter.next();
            }

            int n = ptsVector.size();
            Point3f[] vertices = new Point3f[2 * n];
            Transform3D trans = new Transform3D();
            trans.setTranslation(new Vector3f(0, 0, depth));
            for (int i = 0; i < n; i += 2) {
                Point3f pt = (Point3f) ptsVector.get(i);
                vertices[i] = pt;
                Point3f pt2 = (Point3f) ptsVector.get(i + 1);
                trans.transform(pt2);
                vertices[i + 1] = pt2;
            }

            Vector indicesVector = new Vector();
            for (int i = 0; i < n; i += 2) {
                indicesVector.add(i);
            }
            for (int i = 1; i < n; i += 2) {
                indicesVector.add(i);
            }
            for (int i = 0; i < n; i += 2) {
                indicesVector.add(i);
                indicesVector.add(i + 1);
                indicesVector.add((i + 3) % (n));
                indicesVector.add((i + 2) % (n));
            }
            int[] indices = new int[indicesVector.size()];
            for (int i = 0; i < indicesVector.size(); i++) {
                indices[i] = (int) indicesVector.elementAt(i);
            }

            Vector stripVector = new Vector();
            stripVector.add(n / 2);
            stripVector.add(n / 2);
            for (int i = 0; i < n / 2; i++) {
                stripVector.add(4);
            }
            int[] stripCounts = new int[stripVector.size()];
            for (int i = 0; i < stripVector.size(); i++) {
                stripCounts[i] = (int) stripVector.elementAt(i);
            }

            GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
            gi.setCoordinates(vertices);
            gi.setCoordinateIndices(indices);
            gi.setStripCounts(stripCounts);
            NormalGenerator ng = new NormalGenerator();
            ng.generateNormals(gi);
            this.setGeometry(gi.getGeometryArray());
        }
    }

}

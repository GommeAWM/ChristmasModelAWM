package reyd.Command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import reyd.CModelMain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChristmasCMD extends Command {

    /*
     *
     * Merry Christmas
     *
     * */

    public ChristmasCMD(){
        super("christmas");
        this.commandParameters.clear();
        this.commandParameters.put("snowman", new CommandParameter[]{
                CommandParameter.newEnum("christmas", new CommandEnum("Csnowman", "snowman")),
        });
        this.commandParameters.put("gift", new CommandParameter[]{
                CommandParameter.newEnum("christmas", new CommandEnum("Cgift", "gift")),
        });
        this.commandParameters.put("dog", new CommandParameter[]{
                CommandParameter.newEnum("christmas", new CommandEnum("Cdog", "dog")),
        });
        this.commandParameters.put("remove", new CommandParameter[]{
                CommandParameter.newEnum("christmas", new CommandEnum("Cremove", "remove")),
        });
        this.commandParameters.put("help", new CommandParameter[]{
                CommandParameter.newEnum("christmas", new CommandEnum("Chelp", "help")),
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;

            if (!player.hasPermission("reyd.christmas")){
                return true;
            }

            if (args.length == 1){

                switch (args[0]){

                    case "snowman": {
                        generatorModel(player, "snowman.png", "snowman.json", "snowman");
                        break;
                    }

                    case "gift": {
                        generatorModel(player, "gift.png", "gift.json", "gift");
                        break;
                    }

                    case "dog": {
                        generatorModel(player, "dog.png", "dog.json", "dog");
                        break;
                    }

                    case "remove": {
                        CModelMain.getInstance().christmasEdit.add(player.getName());
                        break;
                    }

                    case "help": {
                        help(player);
                        break;
                    }

                    default: {
                        help(player);
                        break;
                    }

                }

            } else if (args.length == 2){

                switch (args[0]){

                    case "snowman": {
                        generatorModel(player, "snowman.png", "snowman.json", "snowman", args[1]);
                        break;
                    }

                    case "gift": {
                        generatorModel(player, "gift.png", "gift.json", "gift", args[1]);
                        break;
                    }

                    case "dog": {
                        generatorModel(player, "dog.png", "dog.json", "dog", args[1]);
                        break;
                    }

                    default: {
                        help(player);
                        break;
                    }

                }

            } else {
                help(player);
            }

        }

        return true;
    }

    private void help(Player player){
        player.sendMessage("§l§a--- CHRISTMAS MODEL HELP ---");
        player.sendMessage("§3Spawn Entity without custom name: §e/christmas snowman/gift/dog");
        player.sendMessage("§3Spawn Entity with custom name: §e/christmas snowman/gift/dog <custom name>");
        player.sendMessage("§3Remove Entity godmode (): §e/npc remove");
    }

    public void generatorModel(Player player, String skinDName, String geometryDName, String geometryName){
        Skin skin;

        Path path = CModelMain.getInstance().getDataFolder().toPath();
        Path skinPath = path.resolve(skinDName);
        Path geometryPath = path.resolve(geometryDName);

        try {
            skin = createSkin(geometryName, skinPath, geometryPath);
            createModel(player.getPosition(), skin, "", player);
        } catch (IOException var20) {
            var20.printStackTrace();
        }
    }

    public void generatorModel(Player player, String skinDName, String geometryDName, String geometryName, String customName){

        Path path = CModelMain.getInstance().getDataFolder().toPath();
        Path skinPath = path.resolve(skinDName);
        Path geometryPath = path.resolve(geometryDName);

        try {
            Skin skin = createSkin(geometryName, skinPath, geometryPath);
            createModel(player.getPosition(), skin, customName, player);
        } catch (IOException var20) {
            var20.printStackTrace();
        }
    }

    public static Skin createSkin(String name, Path skinPath, Path geometryPath) throws IOException {

        Skin skin = new Skin();
        skin.setSkinData(ImageIO.read(new File(String.valueOf(skinPath.toFile()))));
        skin.setGeometryName("geometry." + name);
        skin.setGeometryData(new String(Files.readAllBytes(geometryPath)));

        return skin;
    }

    public static void createModel(Position position, Skin skin, String name, Player p) {

        byte[] skinData = skin.getSkinData().data;
        BufferedImage skinBuffer = new BufferedImage(skin.getSkinData().width, skin.getSkinData().height, BufferedImage.TYPE_INT_ARGB);
        int index;
        for (int x = 0; x < skinBuffer.getWidth(); x++) {
            for (int y = 0; y < skinBuffer.getHeight(); y++) {
                index = (x + y * skinBuffer.getWidth()) * 4;
                skinBuffer.setRGB(x, y, new Color(skinData[index] & 0xFF, skinData[index + 1] & 0xFF,
                        skinData[index + 2] & 0xFF, skinData[index + 3] & 0xFF).getRGB());
            }
        }


        try {
            ImageIO.write(skinBuffer, "png", new File("skin.png"));
        } catch (IOException ex) {
            //Ignore
        }


        CompoundTag nbt = (new CompoundTag()).putList((new ListTag("Pos")).add(new DoubleTag("", p.x)).add(new DoubleTag("", p.y)).add(new DoubleTag("", p.z))).putList((new ListTag("Motion")).add(new DoubleTag("", 0.0)).add(new DoubleTag("", 0.0)).add(new DoubleTag("", 0.0))).putList((new ListTag("Rotation")).add(new FloatTag("", (float)((int)p.getYaw()))).add(new FloatTag("", (float)((int)p.getPitch())))).putBoolean("Invulnerable", true).putString("NameTag", name).putBoolean("chm", true);
        CompoundTag skinTag = (new CompoundTag()).putByteArray("Data", skin.getSkinData().data).putInt("SkinImageWidth", skin.getSkinData().width).putInt("SkinImageHeight", skin.getSkinData().height).putString("ModelId", skin.getSkinId()).putByteArray("SkinResourcePatch", skin.getSkinResourcePatch().getBytes(StandardCharsets.UTF_8)).putByteArray("GeometryData", skin.getGeometryData().getBytes(StandardCharsets.UTF_8)).putBoolean("IsTrustedSkin", true);
        nbt.putCompound("Skin", skinTag);

        EntityHuman entityHuman = new EntityHuman(position.getChunk(), nbt);
        p.sendMessage("§aYou have successfully created a Christmas entity (model)");

//
        entityHuman.spawnToAll();

    }

}

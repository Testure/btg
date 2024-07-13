package turing.btg.modularui.impl;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import turing.btg.interfaces.INetClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketOpenModularUI extends Packet {
	public String windowTitle;
	public int windowId;
	public int blockX;
	public int blockY;
	public int blockZ;

	public PacketOpenModularUI() {}

	public PacketOpenModularUI(int windowId, String windowTitle, int blockX, int blockY, int blockZ) {
		this.windowId = windowId;
		this.windowTitle = windowTitle;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		windowId = dataInputStream.readByte();
		windowTitle = dataInputStream.readUTF();
		blockX = dataInputStream.readInt();
		blockY = dataInputStream.readInt();
		blockZ = dataInputStream.readInt();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeByte(windowId);
		dataOutputStream.writeUTF(windowTitle);
		dataOutputStream.writeInt(blockX);
		dataOutputStream.writeInt(blockY);
		dataOutputStream.writeInt(blockZ);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((INetClientHandler) netHandler).handleOpenModularUI(this);
	}

	@Override
	public int getPacketSize() {
		return 8 + windowTitle.length() + 5;
	}
}

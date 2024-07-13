package turing.btg.interfaces;

import turing.btg.modularui.impl.PacketOpenModularUI;

public interface INetClientHandler {
	void handleOpenModularUI(PacketOpenModularUI packet);
}

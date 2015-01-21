package com.cecer1.hypixelutils.features.partyautoremove;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyAutoRemoveProcessor implements IOnChatEventHandler {

    private static Pattern _errorMessagePattern = Pattern.compile("^Your party can't queue for (.*): (.*) because (?:|\\[.+\\] )(\\S+) isn't online\\!$");

    @Override
    public void onChat(IOnChatEventData event) {
        if(!HypixelUtilsCore.config.isPartyAutoRemoveOfflineEnabled())
            return;

        IChatComponent rootComponent = ChatUtilities.getRootChatComponent(event.getMessage());

        Matcher matcher = _errorMessagePattern.matcher(event.getMessage().getUnformattedText());
        if(matcher.matches())
        {
            event.setCanceled(true);
            String offlinePlayerName = matcher.group(3);

            HypixelUtilsCore.sendChatMessage("/party remove " + offlinePlayerName);
            ChatOutputs.printPartyAutoRemovedNotice(offlinePlayerName);
        }
    }
}

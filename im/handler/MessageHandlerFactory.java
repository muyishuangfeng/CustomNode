package com.yk.silence.customnode.im.handler;

import android.util.SparseArray;

import com.yk.silence.customnode.im.message.MessageType;


/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       MessageHandlerFactory.java</p>
 * <p>@PackageName:     com.freddy.chat.im.handler</p>
 * <b>
 * <p>@Description:     消息处理handler工厂</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 03:44</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class MessageHandlerFactory {

    private MessageHandlerFactory() {

    }

    private static final SparseArray<IMessageHandler> HANDLERS = new SparseArray<>();

    static {
        /** 单聊消息处理handler */
        HANDLERS.put(MessageType.SINGLE_CHAT.getMsgType(), new SingleChatMessageHandler());
        /** 群聊消息处理handler */
        HANDLERS.put(MessageType.GROUP_CHAT.getMsgType(), new GroupChatMessageHandler());
        /** 服务端返回的消息发送状态报告处理handler */
        HANDLERS.put(MessageType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType(), new ServerReportMessageHandler());
    }

    /**
     * 根据消息类型获取对应的处理handler
     *
     * @param msgType
     * @return
     */
    public static IMessageHandler getHandlerByMsgType(int msgType) {
        return HANDLERS.get(msgType);
    }
}

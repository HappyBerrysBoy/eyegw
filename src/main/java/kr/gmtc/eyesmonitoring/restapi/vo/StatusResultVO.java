package kr.gmtc.eyesmonitoring.restapi.vo;

// sample data
// {
// result: [
// {
// Header: { MsgGroup: 0, SendDT: "2019-10-22 16:42:27", MsgID: 1 },
// Data: { Start: "2019-10-22 16:37:14", SendBuffer: 80, RecvBuffer: 90 }
// }
// ]
// }

public class StatusResultVO {
  private HeaderVO Header;
  private DataVO Data;

  public StatusResultVO() {
  }

  public HeaderVO getHeader() {
    return Header;
  }

  public void setHeader(HeaderVO header) {
    Header = header;
  }

  public DataVO getData() {
    return Data;
  }

  public void setData(DataVO data) {
    Data = data;
  }

  class HeaderVO {
    private int MsgGroup;
    private String SendDT;
    private int MsgID;

    public HeaderVO() {
    }

    public int getMsgGroup() {
      return MsgGroup;
    }

    public void setMsgGroup(int msgGroup) {
      MsgGroup = msgGroup;
    }

    public String getSendDT() {
      return SendDT;
    }

    public void setSendDT(String sendDT) {
      SendDT = sendDT;
    }

    public int getMsgID() {
      return MsgID;
    }

    public void setMsgID(int msgID) {
      MsgID = msgID;
    }
  }

  class DataVO {
    private String Start;
    private float SendBuffer;
    private float isRecvBuffer;

    public DataVO() {

    }

    public String getStart() {
      return Start;
    }

    public void setStart(String start) {
      Start = start;
    }

    public float getSendBuffer() {
      return SendBuffer;
    }

    public void setSendBuffer(float sendBuffer) {
      SendBuffer = sendBuffer;
    }

    public float getIsRecvBuffer() {
      return isRecvBuffer;
    }

    public void setIsRecvBuffer(float isRecvBuffer) {
      this.isRecvBuffer = isRecvBuffer;
    }
  }
}

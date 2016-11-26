package org.ums.response.type;

import org.ums.domain.model.immutable.TaskStatus;

public class TaskStatusResponse implements GenericResponse<TaskStatus> {
  private TaskStatus mTaskStatus;
  private ResponseType mResponseType;
  private String mMessage;

  public TaskStatusResponse(TaskStatus pTaskStatus) throws Exception {
    mTaskStatus = pTaskStatus;
    mResponseType = ResponseType.INFO;
    mMessage = pTaskStatus.getId();
  }

  @Override
  public ResponseType getResponseType() {
    return mResponseType;
  }

  @Override
  public void setResponseType(ResponseType pResponseType) {
    mResponseType = pResponseType;
  }

  @Override
  public String getMessage() {
    return mMessage;
  }

  @Override
  public void setMessage(String pMessage) {
    mMessage = pMessage;
  }

  @Override
  public TaskStatus getResponse() {
    return mTaskStatus;
  }

  @Override
  public void setResponse(TaskStatus pResponse) {
    mTaskStatus = pResponse;
  }
}

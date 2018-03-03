package it.unibo.akka.fileSize;

public class FileSizeMsg {
	public final long size;
	public FileSizeMsg(final long fileSize) { size = fileSize; }
}

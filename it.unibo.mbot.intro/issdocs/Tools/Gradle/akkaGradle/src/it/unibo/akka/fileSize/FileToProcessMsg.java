package it.unibo.akka.fileSize;

public class FileToProcessMsg {
	public final String fileName;
	public FileToProcessMsg(final String name) { fileName = name; }
}

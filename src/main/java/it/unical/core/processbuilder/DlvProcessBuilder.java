package it.unical.core.processbuilder;

public class DlvProcessBuilder implements IProcessBuilder {

	@Override
	public ProcessBuilder getCompileProcessBuilder(String file) {
		return new ProcessBuilder("");
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file) {
		return new ProcessBuilder("dlv", "-silent", "-nofacts", file);
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file, String input) {
		return new ProcessBuilder("dlv", "-silent", "-nofacts", file, input);
	}
	
	@Override
	public boolean compile() {
		return false;
	}

}

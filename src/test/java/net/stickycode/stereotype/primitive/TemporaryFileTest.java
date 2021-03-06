package net.stickycode.stereotype.primitive;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class TemporaryFileTest {

  @Test(expected = NullPointerException.class)
  public void nullExcepts() {
    new TemporaryFile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankExcepts() {
    new TemporaryFile("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void whitespaceExcepts() {
    new TemporaryFile(" ");
  }

  @Test
  public void exists() {
    File file = new TemporaryFile("blah.pmt").getTemporaryFile();
    assertThat(file).exists();
    assertThat(file.canWrite()).isTrue();
    assertThat(file.getName()).startsWith("blah");
    assertThat(file.getName()).endsWith(".pmt");
  }

  @Test
  public void existsNoSuffix() {
    File file = new TemporaryFile("blah").getTemporaryFile();
    assertThat(file).exists();
    assertThat(file.canWrite()).isTrue();
    assertThat(file.getName()).startsWith("blah");
    assertThat(file.getName()).endsWith(".tmp");
  }

  @Test(expected = TemporaryFileCreationFailedException.class)
  public void failure() {
    new TemporaryFile("fail.ed") {

      @Override
      File create(String prefix, String suffix)
          throws IOException {
        throw new IOException("fail");
      }
    };
  }

}

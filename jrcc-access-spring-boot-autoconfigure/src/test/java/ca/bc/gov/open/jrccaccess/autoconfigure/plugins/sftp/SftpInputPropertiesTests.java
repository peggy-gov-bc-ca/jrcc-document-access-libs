package ca.bc.gov.open.jrccaccess.autoconfigure.plugins.sftp;

import org.junit.Assert;
import org.junit.Test;

public class SftpInputPropertiesTests {

    @Test
    public void with_null_ssh_key_should_return_null() {
        SftpInputProperties sut = new SftpInputProperties();
        Assert.assertNull(sut.getSshPrivateKey());
    }

    @Test
    public void with_ssh_key_should_return_ssh_key() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setSshPrivateKey("---BEGIN RSA PRIVATE KEY ----\n1111");
        Assert.assertNotNull(sut.getSshPrivateKey());
    }

    @Test
    public void with_remote_directory_should_return_remote_directory() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setRemoteDirectory("/upload");
        Assert.assertEquals(sut.getRemoteDirectory(), "/upload");
    }

    @Test
    public void with_host_should_return_host() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setHost("localhost");
        Assert.assertEquals(sut.getHost(), "localhost");
    }

    @Test
    public void with_port_should_return_integer_value_of_port() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setPort("22");
        Assert.assertEquals(sut.getPort(), Integer.valueOf("22"));
    }

    @Test
    public void with_username_should_return_username() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setUsername("user");
        Assert.assertEquals(sut.getUsername(), "user");
    }

    @Test
    public void with_password_should_return_password() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setPassword("pass");
        Assert.assertEquals(sut.getPassword(), "pass");
    }

    @Test
    public void with_cron_should_return_cron() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setCron("0/5 * * * * *");
        Assert.assertEquals(sut.getCron(), "0/5 * * * * *");
    }

    @Test
    public void with_filter_pattern_should_return_filter_pattern() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setFilterPattern("filter-pattern");
        Assert.assertEquals(sut.getFilterPattern(), "filter-pattern");
    }

    @Test
    public void with_max_message_per_poll_should_return_max_message_per_poll() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setMaxMessagePerPoll("5");
        Assert.assertEquals(sut.getMaxMessagePerPoll(), "5");
    }

    @Test
    public void with_ssh_private_passphrase_should_return_ssh_private_passphrase() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setSshPrivatePassphrase("passphrase");
        Assert.assertEquals(sut.getSshPrivatePassphrase(), "passphrase");
    }

    @Test
    public void default_allow_unknown_key_return_false() {
        SftpInputProperties sut = new SftpInputProperties();
        Assert.assertFalse(sut.isAllowUnknownKeys());
    }

    @Test
    public void set_allow_unknown_key_should_succeed() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setAllowUnknownKeys(true);
        Assert.assertTrue(sut.isAllowUnknownKeys());
    }

    @Test
    public void default_get_known_hosts_return_null() {
        SftpInputProperties sut = new SftpInputProperties();
        Assert.assertNull(sut.getKnownHostFile());
    }

    @Test
    public void default_get_host_returns_localhost() {
        SftpInputProperties sut = new SftpInputProperties();
        Assert.assertEquals(sut.getHost(), "localhost");
    }

    @Test
    public void default_get_port_returns_22() {
        SftpInputProperties sut = new SftpInputProperties();
        Assert.assertEquals(sut.getPort(), Integer.valueOf("22"));
    }

    @Test
    public void default_get_filter_pattern_returns_empty_string() {
        SftpInputProperties sut = new SftpInputProperties();
        Assert.assertEquals(sut.getFilterPattern(), "");
    }

    @Test
    public void set_known_hosts_should_succeed() {
        SftpInputProperties sut = new SftpInputProperties();
        sut.setKnownHostFile("c://test//known_hosts");
        Assert.assertEquals("c://test//known_hosts", sut.getKnownHostFile());
    }
}

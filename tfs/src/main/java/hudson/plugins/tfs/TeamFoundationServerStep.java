//CHECKSTYLE:OFF
package hudson.plugins.tfs;

import com.google.inject.Inject;
import hudson.Extension;
import hudson.plugins.git.UserRemoteConfig;
import hudson.plugins.tfs.model.ChangeSet;
import hudson.scm.SCM;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import jenkins.plugins.git.Messages;
import org.jenkinsci.plugins.workflow.steps.scm.SCMStep;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.interceptor.RequirePOST;

import javax.annotation.Nonnull;
import java.io.IOException;

public final class TeamFoundationServerStep extends SCMStep {

    private final String url;
    private String userName;
    private Secret password;
    private String projectPath;
    @DataBoundConstructor
    public TeamFoundationServerStep(String url) {
        this.url = url;
    }

    @DataBoundSetter
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @DataBoundSetter
    public void setPassword(Secret password) {
        this.password = password;
    }
    @DataBoundSetter
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
    @Nonnull
    @Override
    protected SCM createSCM() {
        //String serverUrl, String projectPath, String workspaceName, String userName, Secret password
        return new TeamFoundationServerScm(url, projectPath, null, userName, password);
    }

    @Extension
    public static final class DescriptorImpl extends SCMStepDescriptor {

        @Inject
        private UserRemoteConfig.DescriptorImpl delegate;

        @Override
        public String getFunctionName() {
            return "tfs";
        }

        @Override
        public String getDisplayName() {
            return "This is a step that does TFS scm or something";
        }

    }
}

package dfzq.controller.viewModel;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-8-14
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class MenuView {
    private String id;

    private String name;

    private String pid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public MenuView() {
    }

    public MenuView(String id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }
}

package dfzq.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dfzq.dao.CompanyDao;
import dfzq.model.Company;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsLoadController {
//
//    private static Map<String, ServiceLoadParameter> parameterHashMap = new HashMap<String, ServiceLoadParameter>();
//
//
//
//
//    @Autowired
//    @Qualifier("H5StatisticsService")
//    private CombineService statisticsService;
//
//
//    @ResponseBody
//    @RequestMapping(value = "/loadDataByService.do")
//    public List loadDataByService(String service_id) {
//
//        ServiceLoadParameter serviceLoadParameter  = parameterHashMap.get(service_id);
//        List statisticsModels = statisticsService.retrieveSummaryModels(serviceLoadParameter);
//        return statisticsModels;
//
//
//    }

//    private List<TreeView> getTreeViewFromSOTPReportModel(List<SOTPReportModel> sotpReportModels) {
//        List<TreeView> treeViews = new ArrayList<TreeView>();
//        int index = 0;
//        for (SOTPReportModel sotpReportModel : sotpReportModels) {
//            TreeView treeView = new TreeView(index++, sotpReportModel.getServiceName() + "_" + sotpReportModel.getServiceOrder(), sotpReportModel.getTotalNum(), sotpReportModel.getAverageTime(), -1, 100.00, sotpReportModel.getType(),sotpReportModel.getSuccessCount(),sotpReportModel.getFailCount());
////            treeView.setSuccessCount(sotpReportModel.getSuccessCount());
////            treeView.setFailCount(sotpReportModel.getFailCount());
//            treeViews.add(treeView);
//            int parentIndex = treeView.getIndex();
//            double totalTime = sotpReportModel.getSuccessCount() * sotpReportModel.getAverageTime();
//            for (OtherReportModel otherReportModel : sotpReportModel.getOtherReportModels()) {
//                double subTotalTime = otherReportModel.getAverageTime() * otherReportModel.getSuccessCount();
//                double radio =-1;
//                if (totalTime != 0 && otherReportModel.getSuccessCount()>0) {
//                    radio = subTotalTime / totalTime * 100;
//                }
//                TreeView subTreeView = new TreeView(index++, otherReportModel.getServiceName() + "_" + otherReportModel.getServiceOrder(), otherReportModel.getTotalNum(), otherReportModel.getAverageTime(), parentIndex, radio, otherReportModel.getType(),otherReportModel.getSuccessCount(),otherReportModel.getFailCount());
////                subTreeView.setSuccessCount(otherReportModel.getSuccessCount());
////                subTreeView.setFailCount(otherReportModel.getFailCount());
//                treeViews.add(subTreeView);
//
//
//            }
//
//
//        }
//
//        return treeViews;
//    }

}

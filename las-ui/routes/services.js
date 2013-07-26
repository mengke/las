
/*
 * GET services listing.
 */

exports.list = function(req, res){
  res.render('services', { title: 'Log Analyzing System - Services', pageId: 'services', 
                modules: { housevisits: '房源浏览记录',  houserecommands: '基于房源相似度的推荐'} });
};
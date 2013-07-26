
/*
 * GET home page.
 */

exports.index = function(req, res){
  res.render('index', { title: 'Log Analyzing System', pageId: 'dashboard'});
};
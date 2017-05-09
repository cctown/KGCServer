function setSelect(obj,opt){
  	var s = document.getElementById(obj);
  	s.value=opt;
 }
function delCdt(obj){
	//obj.parentNode.removeChild(obj.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling);
	obj.parentNode.removeChild(obj.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling);
	obj.parentNode.removeChild(obj.previousElementSibling.previousElementSibling.previousElementSibling);
	obj.parentNode.removeChild(obj.previousElementSibling.previousElementSibling);
	obj.parentNode.removeChild(obj.previousElementSibling);
	obj.parentNode.removeChild(obj.nextElementSibling);
	obj.parentNode.removeChild(obj);
}
function isNum(obj){
	/*var str = obj.value;
	var pattern = /^-?\d+(\.\d+)?$/;
	if(!pattern.test(str)){
		alert("请输入数字！");
		obj.value=0;
	}*/
	if(obj.value!=parseFloat(obj.value,10)){
		alert("请输入数字！");
		obj.value=0;
	}
}
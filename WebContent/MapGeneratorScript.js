function Responsive() {
	var x = document.getElementById("theme");
	if (x.className === "theme-switcher") {
		x.className += " responsive";
	} else {
		x.className = "theme-switcher";
	}
}
function NonResponsive() {
	var x = document.getElementById("theme");
	if (x.className != "theme-switcher") {
		x.className = "theme-switcher";
	}
}
(function() {
	var jsonData = null;
	$.getJSON({
		method : "GET",
		url : "JSONServlet",
		success : function(data) {
			jsonData = data;
			createChart("none");
		},
		error : function() {
			console.log("error......getting string");
		}
	});
	window.createChart = function(theme) {
		var states = jsonData.states;
		var titles = jsonData.titles.text;
		var date = jsonData.date.Last_Date_Upadted;
		var legend = jsonData.legend;
		var labels = jsonData.labels;
		var total = jsonData.total;
		var balloonPopup = jsonData.balloonPopup;
		var element;
		(function changeopacity() {
			document.getElementById('dark').style.opacity = "0.5";
			document.getElementById('black').style.opacity = "0.5";
			document.getElementById('chalk').style.opacity = "0.5";
			document.getElementById('light').style.opacity = "0.5";
			document.getElementById('default').style.opacity = "0.5";
		})()
		$('#Total_CBSESchoolsTrained').text(total.Total_CBSESchoolsTrained);
		$('#TotalCBSESchools').text(total.Total_CBSESchoolsTrained);
		$('#Total_CBSESchoolTeachersTrained').text(total.Total_CBSESchoolTeachersTrained);
		$('#TotalStateSchools').text(total.Total_StateSchoolsTrained);
		$('#Total_StateSchoolTeachersTrained').text(total.Total_StateSchoolTeachersTrained);
		$('#TotalSchools').text(total.Total_StateSchoolsTrained + total.Total_CBSESchoolsTrained);
		$('#TotalTeachers').text(total.Total_CBSESchoolTeachersTrained + total.Total_StateSchoolTeachersTrained);
		switch (theme) {
		case "dark":
			NonResponsive();
			element = document.getElementById('dark');
			element.style.opacity = "1";
			element.style.filter = 'alpha(opacity=100)';
			document.body.style.backgroundColor = "#3f3f4f";
			document.getElementById("theme-text").style.color = "#aaa";
			document.getElementById("icon-text").style.color = "#aaa";
			document.getElementsByTagName("body")[0].style.backgroundImage = "";
			document.getElementsByTagName("body")[0].style.backgroundSize = "";
			break;
		case "black":
			NonResponsive();
			element = document.getElementById('black');
			element.style.opacity = "1";
			element.style.filter = 'alpha(opacity=100)';
			document.body.style.backgroundColor = "#3f3f4f";
			document.getElementById("theme-text").style.color = "#aaa";
			document.getElementById("icon-text").style.color = "#aaa";
			document.getElementsByTagName("body")[0].style.backgroundImage = "";
			document.getElementsByTagName("body")[0].style.backgroundSize = "";
			break;
		case "chalk":
			NonResponsive();
			element = document.getElementById('chalk');
			element.style.opacity = "1";
			element.style.filter = 'alpha(opacity=100)';
			
			document.getElementById("theme-text").style.color = "#aaa";
			document.getElementById("icon-text").style.color = "#aaa";
			document.getElementsByTagName("body")[0].style.backgroundImage = "url('images/bg.jpg')";
			document.getElementsByTagName("body")[0].style.backgroundSize = "cover";
			break;
		case "light":
			NonResponsive();
			element = document.getElementById('light');
			element.style.opacity = "1";
			element.style.filter = 'alpha(opacity=100)';
			document.getElementById("theme-text").style.color = "black";
			document.getElementById("icon-text").style.color = "black";
			document.body.style.backgroundColor = "";
			document.getElementsByTagName("body")[0].style.backgroundImage = "";
			break;
		default:
			NonResponsive();
			element = document.getElementById('default');
			element.style.opacity = "1";
			element.style.filter = 'alpha(opacity=100)';
			document.getElementById("theme-text").style.color = "black";
			document.getElementById("icon-text").style.color = "black";
			document.body.style.backgroundColor = "";
			document.getElementsByTagName("body")[0].style.backgroundImage = "";
			break;
		}
		var map = AmCharts.makeChart("chartdiv", {
			"type" : "map",
			"theme" : theme,
			"colorSteps" : legend.colorSteps,
			"dataProvider" : {
				"mapURL" : "Libraries/ammap/maps/svg/indiaHigh.svg",
				"getAreasFromMap" : true,
				"zoomLevel" : 0.9,
				"areas" : []
			},
			"allLabels" : labels,
			"areasSettings" : {
				"autoZoom" : true,
				"balloonText" : balloonPopup.Textformat
			},
			"valueLegend" : {
				"right" : 10,
				"minValue" : legend.minValueText,
				"maxValue" : legend.maxValueText
			},
			"zoomControl" : {
				"minZoomLevel" : 0.9
			},
			"titles" : [ {
				"text" : titles,
				"size" : 14
			}, {
				"text" : date,
				"bold" : true,
				"size" : 15
			} ],
			"listeners" : [ {
				"event" : "init",
				"method" : setMapdata
			} ]

		});
		function generateDescription(stateObj) {
			var description = "";
			var CBSEdata = stateObj.data;
			var cmnDescTxt = jsonData.commonDescriptionText;

			if (CBSEdata) {
				if (typeof CBSEdata.CBSESchoolsTrained !== "undefined"
						&& typeof CBSEdata.TotalCBSESchools !== "undefined"
						&& (CBSEdata.CBSESchoolsTrained >= 0 || CBSEdata.TotalCBSESchools >= 0)) {
					description += cmnDescTxt.CBSESchools.replace(
							/\[\[CBSESchoolsTrained\]\]/g,
							CBSEdata.CBSESchoolsTrained).replace(
							/\[\[TotalCBSESchools\]\]/g,
							CBSEdata.TotalCBSESchools).replace(
							/\[\[TeachersTrained\]\]/g,
							CBSEdata.TeachersTrained);
				}
			}
			return description;
		}
		function setMapdata(event) {
			var map = event.chart;
			if (map.dataGenerated)
				return;
			if (map.dataProvider.areas.length === 0) {
				setTimeout(setMapdata, 100);
				return;
			}
			var cnt = 1;
			for (var i = 0; i < map.dataProvider.areas.length; i++) {
				// Dummy State is induced to make it 100% value.
				if (map.dataProvider.areas[i].title === "Dummy") {
					map.dataProvider.areas[i].value = 6; // Grade value 6
					// means 100%
					map.dataProvider.areas[i].description = "";
					continue;
				}
				var grade = 0;
				var schoolsTrained = states[map.dataProvider.areas[i].title].data.CBSESchoolsTrained;
				var totalSchools = states[map.dataProvider.areas[i].title].data.TotalCBSESchools;

				var percentageAchieved = (schoolsTrained * 100) / totalSchools;

				if (percentageAchieved === 0) {
					grade = 0;
				} else if (percentageAchieved > 0 && percentageAchieved <= 5) {
					grade = 1;
				} else if (percentageAchieved > 5 && percentageAchieved <= 25) {
					grade = 2;
				} else if (percentageAchieved > 25 && percentageAchieved <= 50) {
					grade = 3;
				} else if (percentageAchieved > 50 && percentageAchieved <= 75) {
					grade = 4;
				} else if (percentageAchieved > 75 && percentageAchieved < 100) {
					grade = 5;
				} else if (percentageAchieved === 100) {
					grade = 6;
				}
				map.dataProvider.areas[i].value = grade;
				map.dataProvider.areas[i].description = generateDescription(
						states[map.dataProvider.areas[i].title]).replace(
						/\[\[PercentageCovered\]\]/g,
						(Math.round(percentageAchieved * 10) / 10) + "%");
			}
			map.dataGenerated = true;
			map.validateNow();
		}
	}
})();

/*
 * Modified version of ...
 * 
 * Jquery 'Paginator' plug-in
 * Author: Brandon Lee Jennings
 * www.DemiGodDesign.com
 */

(function($) {

	$.fn.paginate = function(numberofrowstodisplayperpage, animationspeed) {

		var tableid = this.attr("id");
		new TablePaginator(tableid, numberofrowstodisplayperpage,
				animationspeed)

	};
})(jQuery);

// Here's the initial JS Object I wrote for a project. Bascially, this plugin is
// an object of this instanitiated in a jquery call.
function TablePaginator(tableid, numberofrowstodisplayperpage, animationspeed) {

	this.pagenum = 1;
	this.sizeoftable = $("#" + tableid + " tr").length;
	this.count = 1;
	var self = this;

	/* Displays a range of rows, called via a pagenum onclick event */
	this.resetRows = function(start, end) {
		// Were reseting the pagenum navaigator here to indicate which page
		$("#" + tableid + " tr, #" + tableid + "pagenumholder").not("tr:first")
				.hide();
		this.displayRows(start, end);
	}

	/*
	 * Loops through an entire page of rows, displaying them with a delay for
	 * the effect
	 */
	this.displayRows = function(start, end) {
		if (start == end || start == this.sizeoftable) {
			start = end;
			$("#" + tableid + "pagenumholder").show();
		}

		if (start <= end) {
			$("#" + tableid + start).fadeIn(animationspeed);
			start++;
			self.displayRows(start, end);
		}
	}

	this.createPagesandPageholder = function() {
		var beginning;
		var ending;
		var current;

		if (this.sizeoftable <= numberofrowstodisplayperpage) {
			beginning = 1;
			end = this.sizeoftable;
		}

		else {

			$("#" + tableid)
					.after(
							
		/*
		<div class="paging">
			<span id="pagingStats">${paging.start?:''} - ${paging.end?:''} / ${paging.total?:''}</span>
			<span id="pagingActions">
				<a href="?offset=${paging.first?:0}${limit}">First</a>, 
				<a href="?offset=${paging.previous?:0}${limit}">Previous</a>, 
				<a href="?offset=${paging.next?:0}${limit}">Next</a>, 
				<a href="?offset=${paging.last?:0}${limit}">Last</a></span>
			
		</div>
		*/
							'<div class="paging" id="'
									+ tableid
									+ 'pagenumholder"><span id="pagingStats"><div style="display: inline-block;" id="'
									+ tableid
									+ 'currentPage">1</div>&nbsp;/&nbsp;<div style="display: inline-block;" id="'
									+ tableid
									+ 'numOfPage"></div></span><span id="pagingActions"><a id="'
									+ tableid
									+ 'beginning">First</a>&nbsp;<a id="'
									+ tableid
									+ 'rewind">Previous</a>&nbsp;<a id="'
									+ tableid
									+ 'forward">Next</a>&nbsp;<a id="'
									+ tableid + 'end">Last</a></span></div>');
			var numberofpages = Math.ceil(this.sizeoftable
					/ numberofrowstodisplayperpage);

			$("#" + tableid + "numOfPage").text(numberofpages);
			$("#" + tableid + "pagenumholder").hide();

			$("#" + tableid + "beginning").click(function() {

				$("#" + tableid + "currentPage").text(1);
				current = 1;
				ending = numberofrowstodisplayperpage * current;
				beginning = (ending - numberofrowstodisplayperpage) + 1;
				self.resetRows(beginning, ending);

			});

			$("#" + tableid + "rewind").click(function() {

				current = $("#" + tableid + "currentPage").text();
				if (current != 1 && current >= 1 && current <= numberofpages) {
					current--;
					$("#" + tableid + "currentPage").text(current);
					ending = numberofrowstodisplayperpage * current;
					beginning = (ending - numberofrowstodisplayperpage) + 1;
					self.resetRows(beginning, ending);
				}

			});

			$("#" + tableid + "forward")
					.button({
						text : false,
						icons : {
							primary : "ui-icon-seek-next"
						}
					})
					.click(
							function() {

								current = $("#" + tableid + "currentPage")
										.text();
								if (current != numberofpages && current >= 1
										&& current <= numberofpages) {

									current++;
									$("#" + tableid + "currentPage").text(
											current);
									ending = numberofrowstodisplayperpage
											* current;
									beginning = (ending - numberofrowstodisplayperpage) + 1;
									self.resetRows(beginning, ending);
								}

							});

			$("#" + tableid + "end").click(function() {

				$("#" + tableid + "currentPage").text(numberofpages);
				current = numberofpages;
				ending = numberofrowstodisplayperpage * current;
				beginning = (ending - numberofrowstodisplayperpage) + 1;
				self.resetRows(beginning, ending);

			});


		}
	}

	/* Constructor */
	this.construct = function() {
		var counter = 0;
		$("#" + tableid + " tr").not("tr:first").hide();
		$("#" + tableid + " tr").each(function() {
			$(this).attr("id", tableid + counter);
			counter++;
		});
		this.createPagesandPageholder();
		this.displayRows(1, numberofrowstodisplayperpage);

	}

	/* I wish Javascript had more straightforward constructors. */
	this.construct();

}
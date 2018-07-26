<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="leftmenu" id="leftmenu">
	<div id="accordion">
	    <div class="menugroup active" name="setup">
	        <img class="icon" src="images/setup.png" />
	        <span class="text">管理功能</span>
	        <span class="arrow"></span>
	    </div>
		<div class="leftmenuitem first" id="areamanage" group="setup">边界设置</div>
		<div class="leftmenuitem" group="setup" id="community">社区管理</div>
		<div class="leftmenuitem" group="setup" id="communityarea">社区边界</div>
	    <div class="menugroup" name="system">
	        <img class="icon" src="images/system.png" />
	        <span class="text">系统管理</span>
	        <span class="arrow"></span>
	    </div>
		<div class="leftmenuitem first" group="system" id="usermanage" style="display:none;">用户管理</div>
		<div class="leftmenuitem" group="system" id="systemlog" style="display:none;">系统日志</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	$('#leftmenu div.menugroup').click(function () {
	    var $this = $(this);
	    var name = $this.attr('name');
	    var active = $this.hasClass('active');
	    $('#leftmenu div.menugroup').removeClass('active');
	    $('#leftmenu div.leftmenuitem').hide();
	    if (!active) {
	        $this.addClass('active');
	        $('#leftmenu div.leftmenuitem[group="' + name + '"]').show();
	    }
	});
});

</script>


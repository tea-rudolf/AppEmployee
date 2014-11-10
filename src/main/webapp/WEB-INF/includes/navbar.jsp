	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">App Employee</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#" style="color: white">Connected as :
							${sessionScope.email}</a></li>
					<li><a href="logout">Disconnect</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li><a href="/employee">Overview</a></li>
					<li><a href="/time">Manage time</a></li>
					<li><a href="/expenses">Manage expenses</a></li>
					<li><a href="/projects">Manage projects & tasks</a></li>
					<li><a href="/travel">Manage travel</a></li>
				</ul>
			</div>
		</div>
	</div>
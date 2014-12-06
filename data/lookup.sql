select  c.name, f.fund_name
from company c inner join company_availbility a 
on c.id=a.company_id
inner join one_on_one_meeting_request one
on one.company_id= c.id
inner join fund f on one.fund_id=f.id
where a.time_frame_id in (1,2,3) and a.time_frame_id not in (4,5,6,7)
group by  c.name, f.fund_name
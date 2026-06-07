#!/usr/bin/env python3
"""Generate NewConceptData.kt — simple structure, flat lists."""

def esc(s):
    return s.replace("\\", "\\\\").replace('"', '\\"')

def mk(cn, en, g):
    return f'        Triple("{esc(cn)}", "{esc(en)}", "{esc(g)}"),'

# ============ NCE1: First Things First ============
NCE1 = []

NCE1.append(("问候与礼貌", [
("你好。","Hello.","问候"),("早上好。","Good morning.","时间"),("下午好。","Good afternoon.","时间"),
("晚上好。","Good evening.","时间"),("晚安。","Good night.","告别"),("再见。","Goodbye.","告别"),
("回头见。","See you later.","告别"),("明天见。","See you tomorrow.","约定"),
("你好吗？","How are you?","关心"),("我很好，谢谢。","I'm fine, thank you.","礼貌"),
("很高兴见到你。","Nice to meet you.","初次"),("我也很高兴见到你。","Nice to meet you too.","回应"),
("打扰了。","Excuse me.","礼貌"),("对不起。","I'm sorry.","道歉"),("没关系。","It doesn't matter.","回应"),
("谢谢。","Thank you.","感谢"),("不用谢。","You're welcome.","回应"),("非常感谢。","Thank you very much.","加强"),
("请坐。","Please sit down.","祈使"),("请进。","Come in please.","邀请"),
("等一下。","Wait a moment.","请求"),("请说慢一点。","Please speak more slowly.","请求"),
("能再说一遍吗？","Could you say that again?","重复"),("当然可以。","Certainly.","允许"),
("祝你愉快！","Have a nice day!","祝福"),("你也一样。","You too.","回应"),
("一路平安。","Have a safe trip.","送别"),("保重。","Take care.","关心"),
("好久不见。","Long time no see.","重逢"),("你最近怎么样？","How have you been?","关心"),
("我能坐这里吗？","May I sit here?","请求"),("请随便坐。","Please sit anywhere.","邀请"),
("能帮我一个忙吗？","Can you do me a favor?","求助"),("当然，什么事？","Sure, what is it?","应允"),
("多谢你的帮助。","Thanks for your help.","感谢"),("乐于效劳。","Happy to help.","回答"),
("对不起我迟到了。","Sorry I'm late.","道歉"),("没关系请坐。","That's all right, sit down.","原谅"),
("请问现在几点了？","What time is it please?","时间"),("十点二十五分。","It's twenty-five past ten.","时间"),
("你今天看起来很好。","You look great today.","赞美"),("谢谢，你也是。","Thanks, you too.","回应"),
("你周末过得怎么样？","How was your weekend?","闲聊"),("非常愉快。","It was great.","回答"),
("今天有什么计划？","Any plans for today?","询问"),("没什么特别的。","Nothing special.","回答"),
("我先走了。","I'll be going now.","告辞"),("慢走。","Take care.","送别"),
("你什么时候有空？","When are you free?","约时间"),("明天下午。","Tomorrow afternoon.","时间"),
("那明天见吧。","See you tomorrow then.","约定"),("好，不见不散。","OK, see you then.","确认"),
("代我向你家人问好。","Give my regards to your family.","转达"),("谢谢，我会的。","Thanks, I will.","回应"),
("生日快乐！","Happy birthday!","祝福"),("新年快乐！","Happy New Year!","祝福"),
("万事如意！","All the best!","祝福"),("圣诞快乐！","Merry Christmas!","祝福"),
("你叫什么名字？","What's your name?","姓名"),("我叫李明。","My name is Li Ming.","介绍"),
("你是哪国人？","Where are you from?","国籍"),("我来自中国。","I'm from China.","国籍"),
("你做什么工作？","What do you do?","职业"),("我是教师。","I'm a teacher.","职业"),
("你多大了？","How old are you?","年龄"),("我二十五岁。","I'm twenty-five.","年龄"),
]))

NCE1.append(("家庭与住所", [
("这是我家。","This is my home.","家"),("欢迎来我家。","Welcome to my home.","待客"),
("请进。","Come in please.","邀请"),("随便坐。","Make yourself at home.","待客"),
("你家真漂亮。","Your home is beautiful.","赞美"),("谢谢。","Thanks.","回应"),
("你家有几口人？","How many people in your family?","家庭"),("四口人。","Four people.","数量"),
("我有爸妈和妹妹。","I have parents and a younger sister.","成员"),
("你是独生子吗？","Are you an only child?","独生"),("不，有弟弟。","No, I have a younger brother.","否定"),
("我妈妈是医生。","My mother is a doctor.","职业"),("我爸爸是工程师。","My father is an engineer.","职业"),
("我姐姐在上大学。","My sister is at university.","学习"),("弟弟还在上中学。","My brother is still in high school.","学习"),
("你结婚了吗？","Are you married?","婚姻"),("是的。","Yes.","肯定"),
("你有孩子吗？","Do you have children?","孩子"),("有一个儿子。","I have one son.","孩子"),
("这是妻子。","This is my wife.","配偶"),("我们结婚十年了。","We've been married for ten years.","婚龄"),
("你家真大。","Your house is really big.","大小"),("客厅很宽敞。","The living room is spacious.","空间"),
("厨房是开放式的吗？","Is the kitchen open-plan?","厨房"),("是的。","Yes.","肯定"),
("墙上挂着一幅画。","There is a picture on the wall.","There be"),
("桌子上有本书。","There is a book on the table.","位置"),
("书架上有很多书。","There are many books on the shelf.","多物品"),
("冰箱里有牛奶。","There is milk in the fridge.","存量"),
("花园里种满了花。","The garden is full of flowers.","花园"),
("有几间卧室？","How many bedrooms?","房间"),("三间。","Three.","数量"),
("这是你的房间吗？","Is this your room?","房间"),("是的。","Yes.","肯定"),
("你住公寓还是房子？","Apartment or house?","居住"),("公寓。","An apartment.","回答"),
("房租贵吗？","Is the rent expensive?","房租"),("还行吧。","It's okay.","评价"),
("附近有什么设施？","What facilities are nearby?","周边"),("超市和公园。","Supermarket and park.","回答"),
("走路到地铁多久？","How long to walk to the subway?","距离"),("五分钟左右。","About five minutes.","时间"),
("邻居怎么样？","What are your neighbors like?","邻居"),("很安静。","Very quiet.","评价"),
("最喜欢家里哪个角落？","Favorite corner at home?","喜好"),("阳台。","The balcony.","回答"),
("我打算搬家了。","I'm planning to move.","搬家"),("为什么？","Why?","追问"),
("离公司太远了。","Too far from the company.","原因"),("祝你顺利。","Good luck with that.","祝福"),
]))

NCE1.append(("日常作息与习惯", [
("我每天六点起床。","I get up at six every day.","起床"),("你几点起床？","What time do you get up?","询问"),
("一般七点。","Usually at seven.","回答"),("周末睡到九点。","On weekends I sleep until nine.","周末"),
("起床后先刷牙。","After getting up I brush my teeth first.","顺序"),("然后洗脸。","Then I wash my face.","接着"),
("六点半吃早饭。","I have breakfast at six thirty.","早餐"),("喝牛奶吃面包。","Drink milk and eat bread.","早餐"),
("七点出门。","I leave at seven.","出门"),("坐地铁上班。","I take the subway to work.","通勤"),
("路上要三十分钟。","The journey takes thirty minutes.","时间"),("八点开始工作。","I start work at eight.","工作"),
("中午十二点吃午饭。","I have lunch at twelve noon.","午餐"),("在食堂吃。","I eat in the canteen.","地点"),
("下午五点下班。","I get off work at five.","下班"),("下班后去健身房。","After work I go to the gym.","锻炼"),
("每周锻炼三次。","I exercise three times a week.","频率"),("晚饭七点吃。","Dinner at seven.","晚餐"),
("晚饭后喜欢看书。","After dinner I like reading.","休闲"),("有时看电视。","Sometimes watch TV.","偶尔"),
("十一点睡觉。","I go to bed at eleven.","睡觉"),("你几点睡？","What time do you go to bed?","询问"),
("一般十点半。","Usually ten thirty.","回答"),("早起鸟还是夜猫子？","Morning bird or night owl?","类型"),
("我绝对是早起鸟。","I'm definitely a morning bird.","定位"),("没有咖啡我起不来。","I can't get up without coffee.","依赖"),
("午饭吃了什么？","What did you have for lunch?","饮食"),("吃了面条。","I had noodles.","回答"),
("你做饭吗？","Do you cook?","做饭"),("偶尔做。","Occasionally.","频率"),
("喜欢尝试新菜谱。","I like trying new recipes.","尝试"),("拿手的是番茄炒蛋。","Specialty is scrambled eggs with tomatoes.","拿手"),
("什么运动？","What sports?","运动"),("我喜欢跑步。","I like running.","偏好"),
("每天跑五公里。","I run five kilometers a day.","距离"),("跑步让我精力充沛。","Running energizes me.","效果"),
("周末一般做什么？","What do you usually do on weekends?","周末"),("睡到自然醒。","Sleep in.","自然"),
("然后打扫房间。","Then clean the room.","家务"),("下午见朋友。","Meet friends in the afternoon.","社交"),
("经常去喝咖啡。","Often go for coffee.","聚会"),("有时候看电影。","Sometimes watch movies.","娱乐"),
("最近在看什么书？","What book are you reading?","读书"),("在读一本小说。","Reading a novel.","回答"),
("很好看。","It's very good.","评价"),("推荐给你。","I recommend it to you.","推荐"),
("经常熬夜吗？","Do you often stay up late?","熬夜"),("尽量早睡。","I try to sleep early.","习惯"),
("熬夜对身体不好。","Staying up late is bad for health.","建议"),("我知道。","I know.","知道"),
("习惯的力量很强大。","The power of habit is very strong.","感悟"),
]))

NCE1.append(("学校与学习", [
("在哪里上学？","Where do you go to school?","学校"),("北京大学。","Peking University.","回答"),
("学什么专业？","What's your major?","专业"),("计算机科学。","Computer science.","回答"),
("上几年级？","What grade are you in?","年级"),("大二。","Sophomore.","回答"),
("这是新来的同学。","This is our new classmate.","介绍"),("欢迎。","Welcome.","欢迎"),
("翻到第32页。","Turn to page 32.","课堂"),("今天学现在完成时。","Today we'll learn the present perfect tense.","内容"),
("能回答这个问题吗？","Can you answer this question?","提问"),("让我想一想。","Let me think.","思考"),
("我不知道答案。","I don't know the answer.","不知"),("查一下词典吧。","Look it up in the dictionary.","建议"),
("这个词怎么拼写？","How do you spell this word?","拼写"),("请拼出来。","Please spell it out.","请求"),
("能再解释一遍吗？","Could you explain that again?","重新"),("我没听懂。","I didn't understand.","不懂"),
("我明白了。","I see.","理解"),("现在懂了吗？","Do you understand now?","确认"),
("请举手。","Raise your hand.","举手"),("请安静。","Please be quiet.","安静"),
("今天有作业吗？","Any homework today?","作业"),("完成第5页练习。","Finish exercises on page five.","作业"),
("作业什么时候交？","When is homework due?","截止"),("下周一。","Next Monday.","日期"),
("我没做作业。","I didn't do my homework.","未做"),("忘了做。","I forgot to do it.","原因"),
("最喜欢哪门课？","Favorite subject?","喜好"),("最喜欢数学。","I like math best.","偏好"),
("英语有点难。","English is a bit difficult.","难度"),("但我会努力的。","But I'll work hard.","态度"),
("你的英语进步很大。","Your English has improved a lot.","进步"),("谢谢，每天在练习。","Thanks, I practice daily.","秘诀"),
("喜欢这位老师。","I like this teacher.","喜欢"),("讲课很有趣。","The lectures are interesting.","评价"),
("能帮我辅导数学吗？","Can you help me with math?","求助"),("当然，放学后一起学。","Sure, let's study after school.","应允"),
("考试什么时候？","When is the exam?","考试"),("下周三。","Next Wednesday.","日期"),
("复习好了吗？","Have you reviewed well?","复习"),("差不多了有点紧张。","Almost, still a bit nervous.","紧张"),
("祝你考试顺利。","Good luck on your exam.","祝福"),("谢谢。","Thanks.","回应"),
("考得很好。","I did well on the exam.","考好"),("考砸了。","I failed.","考差"),
("别灰心。","Don't lose heart.","安慰"),("功夫不负有心人。","Hard work pays off.","谚语"),
("学海无涯苦作舟。","Learning is an endless sea of hard work.","谚语"),
]))

NCE1.append(("衣食住行", [
("这件外套多少钱？","How much is this coat?","问价"),("三百元。","Three hundred yuan.","价格"),
("太贵了。","Too expensive.","评价"),("有便宜的吗？","Anything cheaper?","讲价"),
("可以试穿吗？","May I try it on?","试穿"),("试衣间在那边。","Fitting room is over there.","指引"),
("有点紧。","It's a bit tight.","尺码"),("有大一号的吗？","Do you have a larger one?","尺码"),
("这件刚好。","This one fits just right.","合适"),("我买了。","I'll take it.","购买"),
("可以刷卡吗？","Can I pay by card?","支付"),("可以的。","Yes, you can.","回答"),
("请给我发票。","Give me the receipt please.","发票"),("好的。","OK.","回答"),
("我想退货。","I'd like to return this.","退货"),("有什么问题？","Is there a problem?","询问"),
("不太合身。","It doesn't fit well.","原因"),("收据带了吗？","Did you bring your receipt?","要求"),
("我想理发。","I'd like a haircut.","理发"),("想剪什么样的？","How would you like it cut?","款式"),
("稍微修剪一下。","Just a trim please.","简单"),("超市在哪里？","Where's the supermarket?","位置"),
("在拐角处。","Around the corner.","方位"),("需要买些蔬菜。","I need to buy some vegetables.","购物"),
("这个新鲜吗？","Is this fresh?","新鲜"),("今天早上刚到。","Just arrived this morning.","回答"),
("多少钱一斤？","How much per pound?","单价"),("十块钱。","Ten yuan.","价格"),
("称两斤苹果。","Weigh me two pounds of apples.","称重"),("结账在哪？","Where's checkout?","结账"),
("请这边排队。","Please line up here.","排队"),("空调坏了。","The air conditioner is broken.","故障"),
("得找人修。","Need to call someone to fix it.","修理"),("天气变冷了。","It's getting cold.","变冷"),
("该换厚被子了。","Time to change to a thick quilt.","换季"),("今天想做大餐。","I want to cook a big meal today.","做饭"),
("忘了买酱油了。","I forgot to buy soy sauce.","忘记"),("我下楼买。","I'll go down and buy some.","补救"),
("你穿这件很好看。","You look great in this.","赞美"),("谢谢，我也很喜欢。","Thanks, I like it too.","回应"),
]))

NCE1.append(("天气与健康", [
("今天天气怎么样？","What's the weather like today?","天气"),("晴朗。","It's sunny.","回答"),
("今天好热。","It's so hot today.","温度"),("多少度？","How many degrees?","温度"),
("三十五度。","Thirty-five degrees.","回答"),("外面冷极了。","It's freezing outside.","极冷"),
("要下雨了。","It's going to rain.","预报"),("别忘了带伞。","Don't forget your umbrella.","提醒"),
("雨停了。","The rain has stopped.","雨停"),("天晴了。","It's cleared up.","转晴"),
("风很大。","The wind is strong.","风"),("今天多云。","It's cloudy today.","多云"),
("天气预报说下雪。","The forecast says it will snow.","雪"),("我喜欢春天。","I like spring.","季节"),
("秋天最舒适。","Autumn is the most comfortable.","偏好"),("夏天太潮湿了。","Summer is too humid.","湿度"),
("你气色不太好。","You don't look well.","关心"),("我有点不舒服。","I'm not feeling well.","生病"),
("你怎么了？","What's wrong?","询问"),("我头疼。","I have a headache.","症状"),
("我感冒了。","I've caught a cold.","感冒"),("发烧了。","I have a fever.","发烧"),
("应该去看医生。","You should see a doctor.","建议"),("已经看过了。","I've already seen one.","回答"),
("量一下体温。","Take my temperature.","请求"),("三十八度五。","Thirty-eight point five.","温度"),
("你发烧了。","You have a fever.","诊断"),("需要吃药。","You need to take medicine.","开药"),
("一天吃三次。","Take it three times a day.","用法"),("饭后服用。","Take after meals.","时机"),
("多喝水。","Drink plenty of water.","嘱咐"),("好好休息。","Get some good rest.","休息"),
("祝你早日康复。","Wish you a speedy recovery.","祝福"),("谢谢关心。","Thanks for your concern.","回应"),
("请了一天病假。","I took a day of sick leave.","请假"),("好好休息。","Rest well.","回复"),
("你的腿怎么了？","What happened to your leg?","受伤"),("打篮球扭伤了。","Sprained it playing basketball.","原因"),
("最近流感严重。","The flu is severe recently.","流感"),("大家注意防护。","Everyone take precautions.","提醒"),
("每周去两次健身房。","I go to the gym twice a week.","频率"),("运动让我感觉很棒。","Exercise makes me feel great.","感受"),
("保持均衡饮食。","Keep a balanced diet.","饮食"),("少吃垃圾食品。","Eat less junk food.","建议"),
("早餐一定要吃。","You must eat breakfast.","强调"),("睡眠不足影响健康。","Lack of sleep affects health.","睡眠"),
("每天至少睡七小时。","At least seven hours of sleep a day.","建议"),
]))

NCE1.append(("交通与出行", [
("公交站在哪里？","Where's the bus stop?","公交"),("往前走两百米。","Go straight ahead two hundred meters.","指路"),
("这趟车去哪？","Where does this bus go?","路线"),("市中心。","City center.","回答"),
("到站告诉我。","Tell me when to get off.","提醒"),("好的。","OK.","应允"),
("我要下车了。","I'm getting off.","下车"),("到火车站怎么走？","How to get to the train station?","火车站"),
("坐地铁2号线。","Take metro line two.","指路"),("哪站下？","Which stop?","站"),
("火车站那一站。","The railway station stop.","回答"),("去哪打出租车？","Where to get a taxi?","出租"),
("路边等就行。","Just wait by the roadside.","回答"),("去机场。","To the airport.","目的地"),
("系好安全带。","Fasten your seat belt.","安全"),("大概多久？","About how long?","时间"),
("不堵车四十分钟。","Forty minutes if no traffic.","估计"),("你有驾照吗？","Do you have a driver's license?","驾照"),
("有去年考的。","Yes, got it last year.","回答"),("这条路在修。","This road is under construction.","施工"),
("绕道走吧。","Let's take a detour.","绕道"),("停车场在哪里？","Where's the parking lot?","停车"),
("地下二层。","Basement level two.","回答"),("停车费多少？","How much for parking?","费用"),
("我迷路了。","I'm lost.","迷路"),("帮我看看地图。","Help me look at the map.","求助"),
("该往哪个方向？","Which direction should we go?","方向"),("那边。","That way.","指路"),
("最近的地铁口在哪？","Nearest subway entrance?","地铁"),("对面。","Across the street.","回答"),
("换乘几号线？","Which line to transfer?","换乘"),("换一号线。","Transfer to line one.","回答"),
("跟导航走。","Follow the GPS.","导航"),("导航不太准。","GPS isn't accurate.","问题"),
("前面堵车了。","There's a traffic jam ahead.","堵车"),("又堵了。","Traffic jam again.","抱怨"),
("走着去吧。","Let's walk there.","步行"),("大概十五分钟。","About fifteen minutes.","步行"),
("骑共享单车。","Ride a shared bike.","共享"),("不会骑车。","I can't ride a bike.","不会"),
("地铁还是打车？","Subway or taxi?","方式"),("地铁更准时。","Subway is more punctual.","偏好"),
]))

NCE1.append(("工作与职业", [
("做什么的？","What do you do?","职业"),("软件工程师。","Software engineer.","回答"),
("在哪家公司？","Which company?","公司"),("在腾讯。","At Tencent.","回答"),
("喜欢你的工作吗？","Do you like your job?","喜好"),("还不错。","Pretty good.","评价"),
("工作压力大吗？","Is it stressful?","压力"),("有时候很忙。","Sometimes very busy.","程度"),
("工作了几年？","How many years?","年限"),("五年了。","Five years.","回答"),
("几点上班？","What time do you start?","上班"),("九点。","Nine o'clock.","回答"),
("加班多吗？","Much overtime?","加班"),("偶尔。","Occasionally.","频率"),
("同事怎么样？","What are your colleagues like?","同事"),("很友善。","Very kind.","评价"),
("今天有个会。","We have a meeting today.","会议"),("几点开始？","What time?","时间"),
("下午三点。","Three in the afternoon.","回答"),("发报告给我。","Send me the report.","文件"),
("项目进展如何？","How's the project going?","项目"),("按计划进行。","Going according to plan.","进度"),
("老板找你。","The boss is looking for you.","传话"),("我马上过去。","I'll go right now.","响应"),
("工资待遇怎么样？","How's the salary?","薪资"),("福利不错。","Good benefits.","回答"),
("想请一天假。","I'd like a day off.","请假"),("填请假单。","Fill in the leave form.","流程"),
("将来想做什么？","What do you want to do in the future?","未来"),("想自己创业。","Start my own business.","创业"),
("想换工作吗？","Want to change jobs?","换工作"),("暂时没有。","Not right now.","当前"),
("工作经验很重要。","Work experience is important.","经验"),("比学历还重要？","More than education?","比较"),
("两者都重要。","Both are important.","平衡"),("怎么平衡工作与生活？","How to balance work and life?","平衡"),
("周末不碰工作。","Don't touch work on weekends.","原则"),("你怎么看加班？","What do you think about overtime?","加班"),
("偶尔可以接受。","Occasionally acceptable.","态度"),("长期不行。","Long-term isn't acceptable.","态度"),
("健康第一。","Health comes first.","观点"),("今天辛苦了一天。","Hard day at work.","辛苦"),
("辛苦啦。","Good job.","安慰"),
]))

NCE1.append(("旅行与休闲", [
("打算去哪里旅游？","Where do you plan to travel?","计划"),("想去云南。","Want to go to Yunnan.","回答"),
("什么时候出发？","When do you leave?","时间"),("下月初。","Early next month.","回答"),
("去几天？","How many days?","天数"),("一星期。","A week.","回答"),
("跟团还是自由行？","Tour group or independent?","方式"),("自由行。","Independent.","回答"),
("订机票了吗？","Booked the flight?","机票"),("订了。","Yes, booked.","回答"),
("酒店也订了？","Hotel booked too?","酒店"),("订了民宿。","Booked a guesthouse.","住宿"),
("有什么旅行建议？","Any travel tips?","建议"),("带够现金。","Bring enough cash.","建议"),
("好的好的。","OK, OK.","答应"),("飞机几点起飞？","What time's the flight?","起飞"),
("上午十点。","Ten in the morning.","回答"),("早点到机场。","Get to the airport early.","提醒"),
("去机场堵车了。","Traffic jam to the airport.","堵车"),("别急来得及。","Don't worry, there's time.","安慰"),
("办登机牌。","Check in please.","登机"),("托运行李。","Check in baggage.","行李"),
("登机口在哪？","Where's the boarding gate?","登机"),("B12。","B12.","回答"),
("飞机要起飞了。","The plane is about to take off.","起飞"),("请关手机。","Turn off phones please.","要求"),
("窗边还是过道？","Window or aisle?","座位"),("窗边。","Window.","回答"),
("降落时耳朵疼。","Ears hurt during landing.","降落"),("嚼口香糖。","Chew gum.","缓解"),
("旅行什么印象最深？","What impressed you most?","印象"),("当地人的热情。","The locals' warmth.","回答"),
("你最喜欢什么运动？","What sports do you like best?","运动"),("打篮球。","Playing basketball.","回答"),
("每周玩几次？","How many times a week?","频率"),("一两次。","Once or twice.","回答"),
("你喜欢看电影吗？","Do you like movies?","电影"),("特别喜欢。","Really like them.","程度"),
("最近有什么好电影？","Any good movies recently?","询问"),("推荐那个科幻片。","Recommend that sci-fi film.","推荐"),
("周末一起去看吧。","Let's go watch on the weekend.","邀约"),("好。","OK.","接受"),
("有什么爱好？","Any hobbies?","爱好"),("喜欢听音乐。","I like listening to music.","回答"),
("什么类型的音乐？","What kind of music?","类型"),("流行和摇滚。","Pop and rock.","偏好"),
("会乐器吗？","Can you play an instrument?","乐器"),("会弹吉他。","I can play guitar.","回答"),
("你喜欢看书吗？","Do you like reading?","读书"),("非常喜欢。","Very much.","程度"),
("喜欢小说吗？","Like novels?","小说"),("对，推理小说。","Yes, mystery novels.","偏好"),
]))

NCE1.append(("饮食与烹饪", [
("你饿不饿？","Are you hungry?","问饿"),("饿了。","I'm hungry.","回答"),
("去吃点东西吧。","Let's go grab something to eat.","提议"),("好。","OK.","同意"),
("喜欢吃什么？","What do you like to eat?","喜好"),("中餐。","Chinese food.","偏好"),
("吃辣吗？","Do you eat spicy food?","辣"),("不太行。","Not really.","回答"),
("微辣还好。","A little spicy is fine.","程度"),("喜欢川菜。","I like Sichuan cuisine.","菜系"),
("需要提前订位吗？","Need a reservation?","订位"),("已经订了。","Already made one.","已订"),
("一共几位？","How many?","人数"),("两位。","Two.","回答"),
("有空桌吗？","Any available tables?","问位"),("有，跟我来。","Yes, follow me.","引位"),
("请拿菜单。","Bring us the menu please.","菜单"),("服务员点菜。","Waiter, we're ready to order.","点菜"),
("特色菜是什么？","What's your specialty?","特色"),("红烧鱼不错。","The braised fish is good.","推荐"),
("这道菜辣吗？","Is this dish spicy?","口味"),("不太辣。","Not too spicy.","回答"),
("点一份这个。","One of this please.","点餐"),("还要什么吗？","Anything else?","追加"),
("够了。","That's enough.","够"),("这道菜太好吃了。","This dish is delicious.","赞美"),
("非常美味。","Very tasty.","评价"),("吃好了吗？","Are you finished?","吃完"),
("吃撑了。","I'm stuffed.","饱"),("可以打包吗？","Can we pack up leftovers?","打包"),
("可以。","Yes.","许可"),("买单。","Check please.","买单"),
("现金还是刷卡？","Cash or card?","支付"),("我来付吧。","Let me pay.","请客"),
("各付各的。","Let's split the bill.","AA"),("自己做饭好还是外卖好？","Home cooking or takeout?","选择"),
("自己做饭好。","Home cooking is better.","偏好"),("便宜又健康。","Cheap and healthy.","优点"),
("你会做菜吗？","Can you cook?","厨艺"),("会几道。","A few dishes.","谦虚"),
("来尝尝我的拿手菜。","Try my signature dish.","展示"),("味道怎么样？","How does it taste?","口味"),
("很不错。","Really good.","赞赏"),("自己动手丰衣足食。","Work with your own hands and you'll have enough.","谚语"),
]))

# ============ NCE2: Practice & Progress ============
NCE2 = []

NCE2.append(("故事与经历", [
("上周末经历了有趣的事。","Something interesting happened last weekend.","叙事"),("遇到了老朋友。","I met an old friend.","偶遇"),
("十年没见了。","Hadn't seen each other for ten years.","时间"),("他变化很大差点没认出来。","He had changed a lot, almost didn't recognize him.","变化"),
("坐下聊天。","Sat down and chatted.","叙旧"),("他去了很多国家。","He'd been to many countries.","经历"),
("曾在非洲做志愿者。","He once volunteered in Africa.","志愿"),("真是一段难忘的经历。","Truly an unforgettable experience.","评价"),
("改变了人生观。","It changed his outlook on life.","影响"),("后来去了南美洲。","Later went to South America.","后续"),
("在雨林待了三个月。","Stayed three months in the rainforest.","细节"),("听起来非常精彩。","Sounds amazing.","反应"),
("真令人羡慕。","I'm so envious.","羡慕"),("还记得去海边那次吗？","Remember that time at the seaside?","回忆"),
("最开心的旅行之一。","One of my happiest trips.","回应"),("天气特别好。","The weather was wonderful.","细节"),
("在沙滩待了一整天。","Spent the whole day on the beach.","活动"),("太阳下山才离开。","Didn't leave until the sun went down.","不舍"),
("永远不会忘记那次。","I'll never forget that time.","情感"),("有什么难忘经历？","Any unforgettable experiences?","询问"),
("差点误了火车那次。","That time I almost missed the train.","话题"),("说来听听。","Tell me about it.","好奇"),
("早上睡过头了。","Overslept that morning.","原因"),("醒来只剩半小时。","Only half an hour left when I woke up.","紧急"),
("飞快收拾东西。","Quickly packed my things.","行动"),("冲出家门拦了出租车。","Rushed out and hailed a taxi.","行动"),
("祈祷别堵车。","Praying for no traffic jam.","心理"),("刚好最后一分钟赶上。","Made it at the very last minute.","结果"),
("像经历了一场冒险。","It felt like an adventure.","感受"),("从那以后再没睡过头。","Never overslept again since then.","改变"),
("每人都有自己的故事。","Everyone has their own story.","哲理"),("重要的是学到什么。","What matters is what you learned.","升华"),
("时间过得真快。","Time flies.","感叹"),("希望十年后还能这样聊天。","Hope we can still chat like this in ten years.","愿望"),
("一定会的。","I'm sure we will.","确信"),("人生苦短及时行乐。","Life is short, enjoy it while you can.","哲理"),
]))

NCE2.append(("旅行与见闻", [
("最喜欢哪里？","What's your favorite place to visit?","问题"),("最喜欢京都。","I like Kyoto the most.","偏好"),
("有什么特别的？","What's special about it?","追问"),("浓郁的传统文化氛围。","The rich traditional cultural atmosphere.","理由"),
("去过几次。","Been there a few times.","共鸣"),("古色古香的寺庙和庭院。","Ancient temples and gardens.","细节"),
("秋天的红叶非常美。","The autumn leaves are stunningly beautiful.","赞美"),("几月去的？","Which month?","时间"),
("十一月中旬。","Mid-November.","回答"),("那是红叶最盛的时候。","That's when autumn leaves peak.","时间节点"),
("待了多久？","How long did you stay?","时长"),("一周。","A week.","回答"),
("感觉时间不够。","Felt there wasn't enough time.","遗憾"),("没全逛完。","Couldn't visit everything.","遗憾"),
("下次一定再去。","Must go again next time.","决心"),("出国旅行遇到过麻烦吗？","Any trouble traveling abroad?","麻烦"),
("行李丢了。","Lost my luggage once.","丢失"),("真麻烦。","So troublesome.","共鸣"),
("等了三天才找回来。","Waited three days to get it back.","经过"),("语言不通是最大难题。","Language barrier is the biggest problem.","难点"),
("怎么解决？","How do you solve it?","方法"),("用翻译软件。","Use translation apps.","方法"),
("手势和微笑全世界通用。","Gestures and smiles work everywhere.","妙招"),("体验过当地生活吗？","Ever experienced local life?","体验"),
("住在一个当地人家中。","Stayed in a local's home.","住宿"),("感觉到他们的热情。","Could feel their warmth.","感受"),
("比住酒店真实多了。","Much more real than staying at a hotel.","对比"),("旅行中最美的是遇到的人。","The most beautiful part is the people you meet.","感悟"),
("有些人永远不会再见到。","Some you'll never see again.","感慨"),("短暂的相遇长久的印象。","Brief encounters, lasting impressions.","深刻"),
("世界很大人生很短。","The world is big, life is short.","哲理"),("趁年轻多去看看吧。","Go see more while you're young.","建议"),
("一个人旅行过吗？","Ever traveled alone?","独旅"),("去过西藏。","Went to Tibet alone.","经历"),
("一个人自由但孤独。","Solo travel is free but lonely.","矛盾"),("可以更好与自己对话。","Can have better conversations with yourself.","优点"),
("旅行让你成为更好的自己。","Travel makes you a better you.","哲理"),
]))

NCE2.append(("工作与成就", [
("最大的成就是什么？","What's your greatest achievement?","成就"),("完成这个项目。","Completing this project.","回答"),
("很有挑战性吧？","Very challenging, right?","挑战"),("压力很大。","Very stressful.","认同"),
("连续加班两个月。","Overtime for two months straight.","付出"),("准时上线时特别满足。","Very satisfying when it went live on time.","满足"),
("努力没白费。","Hard work paid off.","回报"),("怎么看待成功？","What do you think about success?","成功观"),
("成功不仅是赚钱。","Success isn't just about money.","超越"),("实现自我价值更重要。","Realizing your value is more important.","价值观"),
("事业上有什么目标？","What are your career goals?","目标"),("想成为团队负责人。","Want to become a team leader.","回答"),
("需要更强的领导力。","Need stronger leadership.","要求"),("是的，在学习管理知识。","Yes, learning management knowledge.","行动"),
("工作最有趣的是什么？","What's most interesting about work?","乐趣"),("解决问题时的成就感。","The sense of achievement when solving problems.","回答"),
("攻克技术难题特别开心。","So happy when cracking technical problems.","描述"),("这种感觉无法替代。","This feeling is irreplaceable.","强调"),
]))

NCE2.append(("环境与自然", [
("环境保护越来越重要了。","Environmental protection is increasingly important.","观点"),("我也这么想。","I think so too.","赞同"),
("有什么具体环保行动？","Any specific environmental actions?","行动"),("少用塑料袋。","Use fewer plastic bags.","行动"),
("自己带袋子购物。","Bring my own bag when shopping.","习惯"),("垃圾分类做了吗？","Do you sort your garbage?","分类"),
("做了但有时分不清。","Yes, but sometimes unclear.","困惑"),("多学学就清楚了。","You'll get it with practice.","鼓励"),
("周末经常去爬山。","Often go hiking on weekends.","活动"),("呼吸新鲜空气真好。","Breathing fresh air is so nice.","感受"),
("城市里难有这种感觉。","Hard to feel this in the city.","对比"),("见过最美的自然景观？","Most beautiful natural scenery?","提问"),
("大峡谷日出终身难忘。","Grand Canyon sunrise, unforgettable.","回答"),("太阳升起峡谷变成金色。","The canyon turned golden as the sun rose.","描述"),
("觉得自己很渺小。","Felt so small.","感受"),("大自然的力量让人敬畏。","Nature's power is awe-inspiring.","感悟"),
("全球变暖是严重问题。","Global warming is a serious problem.","问题"),("应该做点什么。","We should do something.","呼吁"),
("少开车多乘公交。","Drive less, take more transit.","行动"),("每人贡献一点力量。","Everyone contributes a little.","号召"),("积少成多。","Every little bit adds up.","成语"),
]))

NCE2.append(("科技与未来", [
("现代科技改变了一切。","Modern technology has changed everything.","主题"),("每天用手机多久？","How long on your phone daily?","提问"),
("五六个小时。","Five or six hours.","回答"),("太长了。","Too long.","评价"),
("知道不好可是放不下。","I know it's bad but can't put it down.","自嘲"),("AI会取代人类工作吗？","Will AI replace human jobs?","未来"),
("不会完全取代。","Won't completely replace us.","观点"),("AI会创造新工作。","AI will create new jobs.","补充"),
("需要学习新技能。","Need to learn new skills.","要求"),("想象过十年后吗？","Imagined the world in ten years?","未来"),
("可能到处是自动驾驶。","Autonomous driving might be everywhere.","想象"),("科技应该造福人类。","Technology should benefit humanity.","价值观"),
("滥用科技会带来危险。","Misusing technology can bring dangers.","警告"),("关键是人的选择。","The key is humanity's choices.","哲理"),
]))

NCE2.append(("人际关系", [
("友谊对谁都很重要。","Friendship is important for everyone.","主题"),("你怎么看？","What do you think?","提问"),
("我同意。","I agree.","赞同"),("真正的朋友很难得。","True friends are rare.","评价"),
("怎么定义真正朋友？","How do you define a true friend?","定义"),("需要时出现的人。","Someone who shows up when you need them.","回答"),
("还有就是无话不谈。","And someone you can talk about anything with.","补充"),("好朋友之间需要沟通。","Good friends need to communicate.","交流"),
("有误会及时澄清。","Clear up misunderstandings promptly.","建议"),("沉默让隔阂越来越深。","Silence makes the gap grow deeper.","后果"),
("善于表达感受吗？","Good at expressing your feelings?","提问"),("比较内向。","Quite introverted.","性格"),
("但会试着说出来。","But I try to speak it out.","努力"),("家庭关系也有挑战性。","Family relationships are also challenging.","家庭"),
("父母和孩子间有代沟。","There's a generation gap between parents and children.","代沟"),("多理解和包容。","More understanding and tolerance.","建议"),
("多久和父母通话？","How often do you call your parents?","频率"),("每周至少一次。","At least once a week.","回答"),
("他们总问我吃得好不好。","They always ask if I'm eating well.","细节"),("这就是父母的爱吧。","That's parental love I guess.","感悟"),
]))

NCE2.append(("健康与运动", [
("生命在于运动。","Life lies in movement.","谚语"),("不运动会生锈。","The body will rust without exercise.","比喻"),
("喜欢什么运动？","What sports do you like?","提问"),("游泳和跑步。","Swimming and running.","回答"),
("游泳对关节好。","Swimming is good for the joints.","优点"),("保持健身习惯吗？","Do you keep a fitness routine?","习惯"),
("每周三次力量训练。","Strength training three times a week.","频率"),("加两次有氧。","Plus twice cardio.","补充"),
("饮食有什么讲究？","Any dietary attention?","饮食"),("高蛋白少碳水。","High protein, low carbs.","原则"),
("多喝水。","Drink plenty of water.","补充"),("最难的是坚持。","The hardest part is persistence.","难点"),
("有什么秘诀？","Any tips?","秘诀"),("找到你喜欢的运动。","Find sports you truly enjoy.","回答"),
("这样不觉得是任务。","Then it doesn't feel like a chore.","解释"),("心理健康同样重要。","Mental health is equally important.","心理"),
("不要给自己太大压力。","Don't put too much pressure on yourself.","建议"),("偶尔放空很必要。","Occasionally emptying your mind is necessary.","放松"),
]))

NCE2.append(("学习与教育", [
("你认为什么是好教育？","What do you think is good education?","主题"),("不是填鸭式而是启发式。","Not rote learning but heuristic.","观点"),
("教人思考比教知识更重要。","Teaching how to think is more important than teaching knowledge.","对比"),("怎么看终身学习？","What about lifelong learning?","提问"),
("每个人都必须不断学习。","Everyone must keep learning.","必要性"),("技术更新太快了。","Technology updates too fast.","原因"),
("有什么学习建议？","Any study tips?","建议"),("制定明确学习计划。","Make a clear study plan.","回答"),
("每天坚持学一点。","Stick to learning a bit every day.","方法"),("积少成多就能看到进步。","Progress accumulates naturally.","鼓励"),
("小时候喜欢哪门课？","Favorite subject as a child?","童年"),("历史课。","History.","回答"),
("听故事一样。","It was like listening to stories.","原因"),("好老师可以改变一生。","A good teacher can change a life.","感悟"),
("你遇到过好老师吗？","Have you had good teachers?","提问"),("遇到过。","I have.","回答"),
("让我爱上了阅读。","Made me fall in love with reading.","影响"),("至今还很感激她。","Still grateful to her today.","感激"),
]))

NCE2.append(("成功与失败", [
("成功需要付出什么代价？","What price does success require?","成功"),("时间和努力。","Time and effort.","回答"),
("还必须接受失败。","Must also accept failure.","补充"),("失败是成功之母。","Failure is the mother of success.","谚语"),
("你经历过失败吗？","Have you experienced failure?","失败"),("当然很多次。","Of course, many times.","回答"),
("每次都是成长的机会。","Each time is an opportunity for growth.","态度"),("怎么面对挫折？","How to deal with setbacks?","挫折"),
("先接受自己的情绪。","First accept your emotions.","方法"),("然后寻找解决方案。","Then look for solutions.","方法"),
("不放弃最重要。","Not giving up is most important.","核心"),("坚持就是胜利。","Persistence is victory.","谚语"),
]))

NCE2.append(("习惯与改变", [
("改掉坏习惯真难。","Breaking bad habits is so hard.","习惯"),("有什么好建议吗？","Any good suggestions?","建议"),
("从小目标开始。","Start with small goals.","方法"),("每天进步一点点。","Improve a little bit every day.","方法"),
("21天养成一个好习惯。","21 days to form a good habit.","理论"),("关键是坚持。","The key is persistence.","核心"),
("你想改变什么习惯？","What habit do you want to change?","提问"),("想少刷手机。","Want to use my phone less.","回答"),
("设定每天的上限时间。","Set a daily time limit.","建议"),("睡前不碰手机。","Don't touch the phone before bed.","建议"),
("我试试。","I'll try.","态度"),("相信你可以的。","I believe you can do it.","鼓励"),
]))

# ============ NCE3: Developing Skills ============
NCE3 = []

NCE3.append(("社会议题", [
("现代社会面临很多挑战。","Modern society faces many challenges.","主题"),("贫富差距越来越大。","The wealth gap is getting wider.","举例"),
("政府该采取措施吗？","Should the government take measures?","提问"),("需要社会共同努力。","It requires collective societal effort.","观点"),
("教育是缩小差距的根本途径。","Education is the fundamental way to narrow the gap.","深入"),("怎么看城市化问题？","What about urbanization problems?","提问"),
("城市化带来便利也产生拥堵污染。","Urbanization brings convenience but also congestion and pollution.","两面"),("越来越多人涌向大城市。","More and more people flock to big cities.","现象"),
("小城市和乡村在衰落。","Small cities and villages are declining.","对比"),("这确实是两难问题。","This is indeed a dilemma.","评价"),
("未来社会最大变化是什么？","What will be the biggest change in future society?","未来"),("工作方式的彻底改变。","A complete change in the way we work.","预测"),
("远程办公会越来越普遍。","Remote work will become increasingly common.","细化"),("担心失业吗？","Worried about unemployment?","担忧"),
("有一点但适应是关键。","A bit but adaptation is key.","态度"),("跟不上变化就会被淘汰。","If you can't keep up you'll be left behind.","警示"),
("社会变迁速度前所未有。","The pace of social change is unprecedented.","感受"),("做好迎接挑战的准备。","Be prepared for challenges.","呼吁"),
]))

NCE3.append(("科技与创新", [
("AI正在改变每个行业。","AI is transforming every industry.","主题"),("用过AI助手吗？","Have you used an AI assistant?","提问"),
("用过非常强大。","Yes, very powerful.","回答"),("怎么看AI对教育的影响？","What about AI's impact on education?","提问"),
("AI是强有力的教学辅助。","AI can be a powerful teaching aid.","正面"),("但不能完全替代老师。","But can't fully replace teachers.","限制"),
("最理想模式是人机协作。","The ideal model is human-machine collaboration.","观点"),("担心AI会失控吗？","Worried about AI getting out of control?","担忧"),
("这个问题值得认真思考。","This question deserves serious thought.","谨慎"),("我们需要伦理规范。","We need ethical norms.","建议"),
("科技要为人类服务。","Technology should serve humanity.","价值观"),("科学家要有社会责任感。","Scientists need social responsibility.","呼吁"),
("基因编辑技术怎么看？","What about gene editing technology?","提问"),("可能治愈很多疾病。","It could cure many diseases.","正面"),
("但也存在伦理红线。","But there are ethical red lines.","警示"),("科学不应凌驾于伦理之上。","Science should not override ethics.","原则"),
]))

NCE3.append(("文化与交流", [
("文化多样性是全人类的财富。","Cultural diversity is the wealth of all humanity.","主题"),("全球化加速文化交流。","Globalization accelerates cultural exchange.","现象"),
("经历过文化冲突吗？","Ever experienced culture clash?","提问"),("在英国住过一年。","Lived in the UK for a year.","经历"),
("最大的不同是什么？","What was the biggest difference?","追问"),("时间观念完全不同。","The concept of time was completely different.","回答"),
("英国人非常看重准时。","British people value punctuality very much.","对比"),("迟到被视为不礼貌。","Being late is seen as impolite.","解释"),
("怎么看待文化认同？","How do you see cultural identity?","提问"),("每人都有自己的文化根。","Everyone has their own cultural roots.","观点"),
("吸收外来同时保留传统。","Absorb foreign cultures while preserving traditions.","平衡"),("在全球化中保持独特性？","How to maintain uniqueness in globalization?","难题"),
("坚守核心价值。","Stay true to your core values.","建议"),("保持开放心态。","Keep an open mind.","补充"),
]))

NCE3.append(("人生哲理", [
("人生的意义是需要回答的问题。","The meaning of life is a question to answer.","主题"),("人生的意义是什么？","What is the meaning of life?","提问"),
("这个问题我思考很久了。","I've thought about this for a long time.","深思"),("也许在于体验本身。","Maybe it lies in the experience itself.","回答"),
("不在于终点而在于沿途风景。","Not in the destination but the scenery along the way.","比喻"),("害怕变老吗？","Afraid of getting old?","老"),
("以前怕现在不怕了。","Used to but not anymore.","转变"),("每段年龄都有其独特的美。","Every age has its unique beauty.","智慧"),
("重要的是活得有意义。","What matters is living meaningfully.","核心"),("怎么定义成功的人生？","How to define a successful life?","定义"),
("能让自己和他人幸福的人生。","A life that makes yourself and others happy.","回答"),("钱不是万能的。","Money isn't everything.","常识"),
("但没钱也不行。","But you can't do without it either.","现实"),("找到平衡才是智慧。","Finding balance is wisdom.","总结"),
("活在当下。","Live in the present.","哲理"),("也为未来打算。","But also plan for the future.","平衡"),
("关键在心态。","The key is your mindset.","核心"),
]))

NCE3.append(("经济与商业", [
("全球经济的未来充满不确定性。","The future of the global economy is uncertain.","主题"),("通货膨胀是主要挑战。","Inflation is the main challenge.","议题"),
("普通人如何应对通胀？","How can ordinary people deal with inflation?","提问"),("多元化投资是策略。","Diversified investment is a strategy.","回答"),
("也要注意风险管理。","But also pay attention to risk management.","补充"),("不要把所有鸡蛋放一个篮子里。","Don't put all your eggs in one basket.","谚语"),
("了解股票市场吗？","Do you understand the stock market?","提问"),("了解一点不太深入。","A bit, not in depth.","回答"),
("投资需要知识和耐心。","Investing requires knowledge and patience.","建议"),("贪婪和恐惧是最大敌人。","Greed and fear are the biggest enemies.","警示"),
("理性战胜情绪很难。","Reason overcoming emotion is hard.","现实"),("需要规则和纪律。","We need rules and discipline.","总结"),
]))

NCE3.append(("艺术与文学", [
("艺术是人类精神的表达。","Art is the expression of the human spirit.","定义"),("文学音乐绘画都承载文明。","Literature, music, and painting carry civilization.","列举"),
("最喜欢的小说？","Favorite novel?","提问"),("《百年孤独》。","One Hundred Years of Solitude.","回答"),
("想到了什么？","What does it make you think of?","追问"),("关于孤独、家族和人类命运。","About loneliness, family, and human fate.","回答"),
("伟大作品能超越时代和地域。","Great works transcend era and region.","评价"),("艺术的魅力不可思议。","The charm of art is incredible.","赞叹"),
("在生活中喜欢什么形式的美？","What form of beauty do you like in life?","审美"),("欣赏建筑之美。","Appreciate architectural beauty.","偏好"),
("伟大建筑是一个时代的缩影。","A great building is a microcosm of an era.","深刻"),("建筑是有温度的艺术。","Architecture is art with warmth.","感受"),
("写过诗或文章吗？","Ever written poetry or essays?","创作"),("偶尔写随笔。","Occasionally write essays.","回答"),
("写作让我整理思绪。","Writing helps me organize my thoughts.","功用"),("很有治愈力。","It's very healing.","体会"),
]))

NCE3.append(("科学与探索", [
("科学发现源于好奇心。","Scientific discoveries originate from curiosity.","主题"),("简单问题可能引发重大突破。","Simple questions can lead to major breakthroughs.","观点"),
("牛顿问苹果为什么掉下来。","Newton asked why apples fall down.","举例"),("结果发现了万有引力。","And discovered gravity as a result.","结果"),
("你平时会问为什么吗？","Do you usually ask why?","提问"),("尽量保持好奇心。","I try to keep my curiosity.","回答"),
("有时懒于追根究底。","Sometimes too lazy to dig deeper.","自省"),("思考是费力的。","Thinking is effortful.","安慰"),
("碳排放需全人类合作。","Carbon emissions require all of humanity to cooperate.","全球"),("没有国家可以独自解决。","No country can solve it alone.","强调"),
("怎么推动国际合作？","How to promote international cooperation?","提问"),("首先要达成共识。","First reach consensus.","步骤"),
("然后是行动。","Then comes action.","步骤"),("光说不练没意义。","Talking without action is meaningless.","批评"),
("对太空探索有兴趣吗？","Interested in space exploration?","话题"),("非常感兴趣。","Very interested.","回答"),
("人类探索未知的本能不会消失。","The instinct to explore the unknown won't disappear.","观点"),("总有一天成为多行星物种。","One day we'll be a multi-planetary species.","预测"),
]))

NCE3.append(("个人成长", [
("成长是一个终身的旅程。","Growth is a lifelong journey.","主题"),("反思是成长的必要条件。","Reflection is necessary for growth.","观点"),
("过度反思也会导致焦虑。","Excessive reflection may also lead to anxiety.","限制"),("适度很重要。","Moderation is important.","观点"),
("勇于走出舒适区。","Dare to step out of your comfort zone.","勇气"),("待在原地是最安全的也是最危险的。","Staying put is the safest and also the most dangerous.","悖论"),
("从错误中吸取教训。","Learn from your mistakes.","建议"),("下次做得更好。","Do better next time.","目标"),
("保持学习的心态。","Keep a learning mindset.","心态"),("永远不要觉得自己已经学够了。","Never feel you've learned enough.","警告"),
]))

# ============ NCE4: Fluency in English ============
NCE4 = []

NCE4.append(("高级思辨", [
("哲学不只在书本上。","Philosophy is not just in books.","主题"),("关乎我们如何生活。","It's about how we live.","解释"),
("苏格拉底说未经审视的人生不值得过。","Socrates said the unexamined life is not worth living.","引用"),("同意吗？","Agree?","提问"),
("反思是成长的必要条件。","Reflection is necessary for growth.","立场"),("过度反思也可能导致焦虑。","Excessive reflection may also lead to anxiety.","限制"),
("科学与宗教并非必然对立。","Science and religion are not necessarily opposed.","观点"),("科学解释如何，宗教解释为何。","Science explains how, religion explains why.","区分"),
("各自回答不同层面的问题。","Each answers questions at different levels.","分析"),("兼容比冲突更有智慧。","Compatibility is wiser than conflict.","结论"),
("怎么理解正义？","How to understand justice?","提问"),("不是简单以牙还牙。","Not simply an eye for an eye.","观点"),
("而是对社会公平的追求。","But the pursuit of social fairness.","深化"),("真正的正义需要同理心。","True justice requires empathy.","核心"),
]))

NCE4.append(("全球视野", [
("我们生活在一个互联互通的世界。","We live in an interconnected world.","主题"),("蝴蝶效应真实存在。","The butterfly effect is real.","概念"),
("一国危机可能波及全球。","A crisis in one country can affect the whole world.","分析"),("全球化利大于弊还是弊大于利？","Does globalization do more good than harm?","辩论"),
("取决于如何管理。","It depends on how we manage it.","回答"),("不能因噎废食。","Don't stop eating for fear of choking.","谚语"),
("国际合作前所未有的重要。","International cooperation is more important than ever.","观点"),("各国利益紧密相连。","Countries' interests are closely connected.","现实"),
("没有人是孤岛。","No man is an island.","名言"),("同舟共济才能渡过难关。","We must pull together to overcome difficulties.","号召"),
("你关心国际新闻吗？","Do you follow international news?","习惯"),("每天都会看。","I read it every day.","回答"),
("了解世界才能更好理解自己。","Understanding the world helps you understand yourself.","感悟"),
]))

NCE4.append(("领导力与影响力", [
("真正的领导力不是权力。","True leadership is not about power.","主题"),("而是影响力。","It's about influence.","解释"),
("怎样才算好领导？","What makes a good leader?","提问"),("能激发他人的潜能。","Someone who can inspire others' potential.","回答"),
("以身作则最重要。","Leading by example is most important.","原则"),("说的和做的一致。","Your words and actions should match.","一致"),
("伟大的领导者都是倾听者。","Great leaders are all listeners.","特征"),("他们善于理解不同的声音。","They are good at understanding different voices.","能力"),
("危机时刻领导力更加凸显。","Leadership becomes more prominent in crises.","时刻"),("冷静自信给予团队方向。","Calm confidence gives the team direction.","表现"),
("你想成为什么样的领导者？","What kind of leader do you want to be?","自省"),("能被大家信任的那种。","The kind that can be trusted by everyone.","回答"),
]))

NCE4.append(("时间与记忆", [
("时间是世间最公平的东西。","Time is the fairest thing in the world.","主题"),("每人每天都有二十四小时。","Everyone has twenty-four hours a day.","事实"),
("区别在于如何利用。","The difference is in how we use it.","关键"),("你是时间管理的高手吗？","Are you a time management expert?","提问"),
("还在学习中。","Still learning.","谦虚回答"),("分清轻重缓急是关键。","Prioritizing is key.","方法"),
("不重要的事可以以后再做。","Unimportant things can wait.","策略"),("重要紧急的必须马上处理。","Important and urgent must be handled immediately.","原则"),
("记忆是如何运作的？","How does memory work?","话题"),("我们倾向于记住带情感的东西。","We tend to remember things with emotions.","原理"),
("所以我更记得快乐和痛苦的时刻。","So I remember happy and painful moments better.","引申"),("平平淡淡的反而不容易记起。","The plain and ordinary are harder to recall.","发现"),
("时间会冲淡一切吗？","Does time dilute everything?","提问"),("会，但不会完全消失。","Yes, but not completely.","回答"),
("深刻的记忆会陪伴你一生。","Deep memories will accompany you for life.","总结"),
]))

NCE4.append(("深度阅读", [
("阅读是穿越时空的对话。","Reading is a conversation across time and space.","定义"),("与那些你永远见不到的人交流。","Communicating with people you'll never meet.","升华"),
("你最近读了印象深刻的书？","What book recently impressed you?","提问"),("《1984》。","1984.","回答"),
("那本书给你什么感受？","What feeling did that book give you?","追问"),("关于权力和控制令人警醒。","Sobering thoughts about power and control.","回答"),
("经典之作为何成为经典？","Why do classics become classics?","提问"),("它们触及了永恒的人性问题。","They touch on eternal human problems.","回答"),
("独立于时代却反映时代。","Independent of their era yet reflecting it.","矛盾"),("这是经典的魅力。","That's the charm of classics.","总结"),
("你怎么养成阅读习惯的？","How did you develop a reading habit?","习惯"),("从小父母就带我去图书馆。","My parents took me to the library since I was little.","童年"),
("慢慢就成了生活的一部分。","It gradually became part of my life.","过程"),("就像吃饭睡觉一样自然。","As natural as eating and sleeping.","比喻"),
]))

NCE4.append(("人工智能与伦理", [
("AI到底有没有意识？","Does AI really have consciousness?","话题"),("这个问题没有标准答案目前。","There's no standard answer yet.","现状"),
("但可能不久的将来就会有。","But likely in the near future.","预测"),("我们应该如何与AI相处？","How should we coexist with AI?","问题"),
("就像和另一个物种共存。","Like coexisting with another species.","类比"),("尊重但保持警惕。","Respect but stay vigilant.","建议"),
("AI的伦理问题谁来负责？","Who is responsible for AI ethics?","责任"),("公司、政府和每个使用者。","Companies, governments, and every user.","回答"),
("责任需要分摊到整个社会。","Responsibility needs to be shared across society.","观点"),("无人能置身事外。","No one can be a bystander.","警示"),
]))

NCE4.append(("幸福与满足", [
("幸福是什么？","What is happiness?","永恒"),("一直在变可能永远在变。","Always changing, maybe forever changing.","动态"),
("小时候的快乐和现在就不一样。","Happiness as a child is different from now.","对比"),("也许幸福就是感知当下的能力。","Maybe happiness is the ability to perceive the present.","假设"),
("你相信自己能得到持久的幸福吗？","Do you believe you can achieve lasting happiness?","提问"),("我相信但不执著。","I believe but not obsessively.","态度"),
("追求幸福的过程可能比结果更重要。","The pursuit may be more important than the result.","感悟"),("途中看到的风景全是收获。","The scenery along the way is all gain.","比喻"),
("你后半生最珍惜什么？","What will you cherish most in the second half of life?","反思"),("家人和健康。","Family and health.","回答"),
("平安是福。","Peace is a blessing.","格言"),("不必大富大贵但求心安。","No need for great wealth, just peace of mind.","价值观"),
]))

# ======== GENERATION =========
ALL = [("NCE1", NCE1), ("NCE2", NCE2), ("NCE3", NCE3), ("NCE4", NCE4)]

path = "/Users/moutianrui/WorkBuddy/2026-06-06-17-56-25/app/src/main/java/com/kuayutong/data/seed/NewConceptData.kt"
with open(path, "w", encoding="utf-8") as f:
    f.write("""package com.kuayutong.data.seed

import com.kuayutong.data.entity.SentenceEntity

/**
 * New Concept English (新概念英语) sentence data.
 * Based on the classic textbook series by L.G. Alexander.
 * 4 books covering CEFR A1 to C1 level.
 */
object NewConceptData {

""")
    # Write each scene list
    for book, scenes in ALL:
        sid = 1
        for sname, sentences in scenes:
            vname = f"nce_{book.lower()}_{sid}"
            f.write(f"    val {vname} = listOf(\n")
            for cn, en, grammar in sentences:
                f.write(f'        Triple("{esc(cn)}", "{esc(en)}", "{esc(grammar)}"),\n')
            f.write("    )\n\n")
            sid += 1

    # generateAllSentences
    f.write("    fun generateAllSentences(): List<SentenceEntity> {\n")
    f.write("        val all = mutableListOf<SentenceEntity>()\n\n")
    for book, scenes in ALL:
        sid = 1
        for sname, _ in scenes:
            vname = f"nce_{book.lower()}_{sid}"
            f.write(f'        all.addAll(makeScene("{book}", {sid}, "{sname}", {vname}))\n')
            sid += 1
    f.write("""        return all
    }

    private fun makeScene(level: String, sceneId: Int, sceneName: String, sentences: List<Triple<String, String, String>>): List<SentenceEntity> {
        return sentences.mapIndexed { idx, (cn, en, grammar) ->
            SentenceEntity(
                cefrLevel = level,
                sceneId = sceneId,
                sceneName = sceneName,
                sentenceId = idx + 1,
                chineseSentence = cn,
                englishAnswer = en,
                grammarFocus = grammar
            )
        }
    }
}
""")

# Stats
total = sum(len(s) for _, scenes in ALL for _, s in scenes)
print(f"Done! {path}")
print(f"Total sentences: {total}")
for book, scenes in ALL:
    cnt = sum(len(s) for _, s in scenes)
    print(f"  {book}: {len(scenes)} scenes, {cnt} sentences")

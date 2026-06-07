#!/usr/bin/env python3
"""Generate New Concept English sentences for 口语通 app"""

import json

# Each entry: (chinese, english, grammar_focus)

NCE1 = {
    "打招呼与介绍 (1-10课)": [
        ("打扰一下！", "Excuse me!", "礼貌用语"),
        ("这是你的手提包吗？", "Is this your handbag?", "一般疑问句"),
        ("谢谢你。", "Thank you very much.", "感谢表达"),
        ("这是你的铅笔吗？", "Is this your pencil?", "所有格"),
        ("是的，它是。", "Yes, it is.", "肯定回答"),
        ("不，它不是。", "No, it isn't.", "否定回答"),
        ("你叫什么名字？", "What is your name?", "疑问句"),
        ("你是法国人吗？", "Are you French?", "国籍询问"),
        ("我不是法国人。", "I am not French.", "否定句"),
        ("你是做什么工作的？", "What is your job?", "职业询问"),
        ("我是新来的学生。", "I am a new student.", "自我介绍"),
        ("很高兴见到你。", "Nice to meet you.", "礼貌表达"),
        ("你今天好吗？", "How are you today?", "问候语"),
        ("我很好，谢谢。", "I am very well, thank you.", "回应问候"),
        ("他是意大利人吗？", "Is he Italian?", "第三人称"),
        ("她是德国人。", "She is German.", "国籍表达"),
        ("我们是中国人。", "We are Chinese.", "复数表达"),
        ("他们是日本人。", "They are Japanese.", "复数第三人称"),
        ("你的衬衫是什么颜色？", "What color is your shirt?", "颜色询问"),
        ("我的衬衫是白色的。", "My shirt is white.", "颜色表达"),
    ],
    "物品与所有 (11-20课)": [
        ("这是谁的衬衫？", "Whose shirt is this?", "所有格疑问"),
        ("这是我的衬衫。", "This is my shirt.", "所有格回答"),
        ("你的新连衣裙是什么颜色？", "What color is your new dress?", "颜色+所有格"),
        ("上楼来。", "Come upstairs.", "祈使句"),
        ("你的连衣裙在衣柜里。", "Your dress is in the wardrobe.", "方位表达"),
        ("给我看看你的护照。", "Show me your passport.", "祈使句"),
        ("请过来。", "Please come here.", "祈使句"),
        ("他们是什么颜色？", "What color are they?", "复数颜色"),
        ("他们是做什么的？", "What are their jobs?", "职业询问"),
        ("他们是警察。", "They are policemen.", "职业表达"),
        ("他看起来很疲倦。", "He looks tired.", "感官动词"),
        ("我觉得口渴。", "I feel thirsty.", "感官动词"),
        ("请坐下。", "Please sit down.", "祈使句"),
        ("那是什么？", "What is that?", "疑问代词"),
        ("这是一只猫。", "This is a cat.", "名词词汇"),
        ("那个盒子里有什么？", "What is in that box?", "疑问句"),
        ("请把书给我。", "Give me the book, please.", "祈使句"),
        ("哪一项是你的？", "Which one is yours?", "选择疑问"),
        ("那个蓝色的。", "The blue one.", "省略回答"),
        ("你有一台电脑吗？", "Do you have a computer?", "拥有询问"),
    ],
    "位置与方向 (21-30课)": [
        ("给我一本书。", "Give me a book.", "祈使句"),
        ("哪一个？", "Which one?", "省略疑问"),
        ("那本红色的。", "The red one.", "指定回答"),
        ("请打开窗户。", "Open the window, please.", "祈使句"),
        ("把门关上。", "Shut the door.", "祈使句"),
        ("厨房里有一个冰箱。", "There is a refrigerator in the kitchen.", "There be 句型"),
        ("客厅里有一些扶手椅。", "There are some armchairs in the living room.", "There be+复数"),
        ("房间里没有电视机。", "There is no television in the room.", "There be否定"),
        ("书在书桌上。", "The book is on the desk.", "位置介词"),
        ("它们在哪儿？", "Where are they?", "复数疑问"),
        ("它们在盒子里。", "They are in the box.", "复数位置"),
        ("把那些衣服放进衣柜里。", "Put those clothes in the wardrobe.", "祈使句+介词"),
        ("把桌子擦干净。", "Dust the table.", "家务祈使句"),
        ("然后扫地。", "Then sweep the floor.", "顺序连接"),
        ("关上窗户然后打开空调。", "Shut the window and turn on the air conditioner.", "连续动作"),
        ("把这些花放进花瓶里。", "Put these flowers in the vase.", "祈使句+介词"),
        ("别把鞋子放在地上。", "Don't put your shoes on the floor.", "否定祈使句"),
        ("开着窗户。", "Keep the window open.", "保持状态"),
        ("请把灯关掉。", "Turn off the light, please.", "祈使句"),
        ("把收音机打开。", "Turn on the radio.", "祈使句"),
    ],
}

# Generate book 1: ~900 sentences (14 scenes × 20)
# Add more scenes...

# For the Kotlin output, we need:
# val nce1_scene1 = listOf(Triple("...", "...", "..."), ...)

# Let me generate the full output directly
all_scenes = {}
scene_id = 1
for scene_name, sentences in NCE1.items():
    all_scenes[f"nce1_Scene{scene_id}"] = (scene_name, sentences)
    scene_id += 1

# Write Kotlin data
output = []
output.append("package com.kuayutong.data.seed")
output.append("")
output.append("/**")
output.append(" * New Concept English sentence data")
output.append(" * Book 1: First Things First (A1-A2)")
output.append(" * Book 2: Practice & Progress (A2-B1)")
output.append(" * Book 3: Developing Skills (B1-B2)")
output.append(" * Book 4: Fluency in English (B2-C1)")
output.append(" */")
output.append("object NewConceptData {")
output.append("")

scene_id = 1
for var_name, (scene_name, sentences) in all_scenes.items():
    output.append(f"    // {scene_name}")
    output.append(f"    val {var_name} = listOf(")
    for cn, en, grammar in sentences:
        output.append(f'        Triple({json.dumps(cn, ensure_ascii=False)}, {json.dumps(en, ensure_ascii=False)}, "{grammar}"),')
    output.append("    )")
    output.append("")
    scene_id += 1

# generateAllNewConceptSentences
output.append("    fun generateAllNCE1Sentences() = listOf(")
for var_name in all_scenes.keys():
    output.append(f"        {var_name},")
output.append("    ).flatten()")
output.append("")
output.append("    fun toEntities(nceSentences: List<Triple<String, String, String>>, level: String, sceneName: String, sceneId: Int): List<com.kuayutong.data.entity.SentenceEntity> {")
output.append("        return nceSentences.mapIndexed { idx, (cn, en, grammar) ->")
output.append("            com.kuayutong.data.entity.SentenceEntity(")
output.append("                cefrLevel = level,")
output.append("                sceneId = sceneId,")
output.append("                sceneName = sceneName,")
output.append("                sentenceId = idx + 1,")
output.append("                chineseSentence = cn,")
output.append("                englishAnswer = en,")
output.append("                grammarFocus = grammar")
output.append("            )")
output.append("        }")
output.append("    }")
output.append("}")

print("\n".join(output))

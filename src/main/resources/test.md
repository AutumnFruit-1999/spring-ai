# 名词解释
**联结主义**：<font style="color:rgb(44, 44, 54);">以神经网络为基础，模拟人脑神经元之间的连接与激活模式，通过分布式表征和并行计算实现认知功能。</font>

**预训练**：事先训练好一个基础模型。<font style="color:rgb(44, 44, 54);">在大规模无标注数据上学习通用特征，构建模型的基础能力</font>

**微调**：<font style="color:rgb(44, 44, 54);">在预训练模型基础上，使用特定任务的小规模标注数据进一步训练，优化模型性能。</font>

**推理**：<font style="color:rgb(44, 44, 54);">模型在训练完成后，对新输入数据进行预测或生成的过程。</font>

**RAG**：<font style="color:rgb(44, 44, 54);">从向量数据库中检索与查询相关的文档片段，将检索结果作为上下文注入生成模型，结合检索信息生成最终答案。</font>

**词嵌入**：把文本转换成词向量的形式，<font style="color:rgb(44, 44, 54);">将词语映射为低维向量，捕捉语义关系。</font>

**向量数据库**：<font style="color:rgb(44, 44, 54);">存储和管理高维向量数据，支持快速检索。</font>

**向量检索**：找到词的相关性，点积，或者余弦值，<font style="color:rgb(44, 44, 54);">衡量向量夹角，适用于语义匹配。计算向量内积，强调方向一致性。</font>

**PGC**：<font style="color:rgb(44, 44, 54);">专家创作，高质量但成本高。学术论文、专业报告。</font>

**UGC**：<font style="color:rgb(44, 44, 54);">用户生成，多样性高但质量参差。</font>

**AIGC**：有AI创作的内容，<font style="color:rgb(44, 44, 54);">自动化生成，规模化生产。</font>

**AGI**：<font style="color:rgb(44, 44, 54);">具备人类水平的通用认知能力，可跨领域自主学习。</font>

**多模态**：能够处理多中内容，<font style="color:rgb(44, 44, 54);">通过共享隐空间融合文本、图像、音频。</font>如文件，图片

**工作流**：将多个功能串联成一个流程

**智能体**：封装工具，完成一系列复杂内容

**MCP**：用于操纵外部工具的协议

**A2A**：agent调用agent协议

**蒸馏**：让参数量大的大模型指导参数量小的小模型

**LORA**：<font style="color:rgb(44, 44, 54);">通过低秩矩阵微调模型参数，减少训练参数量。</font>

**RLHF**：人类反馈强化学习

**NLP**：自然语言处理

**CV**：图片相关处理，计算机视觉

**TTS**：语音相关

**ASR**：<font style="color:rgb(44, 44, 54);">实现语音转文本</font>

**Attention机制**：<font style="color:rgb(44, 44, 54);">动态计算输入元素间的相关性，赋予重要元素更高权重。</font>

**神经网络**：就是线性函数套上一个激活函数不断组合成一个复杂函数。

损**失函数**：为了寻找一组更拟合真实数据的函数，通过寻找损失函数最小的的方法来寻找w和b的值，可以直接将损失函数的导数等于0直接求出损失函数的最小值，来获取w和b的值。神经网络的复杂度没办法直接对数据进行求导，只能进行偏导数。

**图片处理**：**卷积神经网络**

**文本处理**：循环神经网络**RNN神经网络**,    串行计算

通过**词嵌入**获取词向量，通过数据训练出来的嵌入矩阵。

GRU和LSTM改进RNN

**Attention机制**：训练一个含有位置信息的qkv向量，分别与词向量进行相乘

# Transform原理
![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752206724224-71e9eaf7-3391-4f6d-a214-3f721fccefa0.png)

Transform 主要由两部分组成Encoder和Decoder两部分组成。

## Encoder
### 获取词向量
第一步：首先获取词的表示向量X。

词向量X = 单词Embedding向量+位置Embedding向量

### <font style="color:rgb(25, 27, 31);">单词 Embedding</font>
<font style="color:rgb(25, 27, 31);">单词的 Embedding 有很多种方式可以获取，例如可以采用 Word2Vec、Glove 等算法预训练得到，也可以在 Transformer 中训练得到。</font>

<font style="color:rgb(25, 27, 31);"></font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752231970674-56d33854-3dcf-4551-94f5-16f71baa29cd.png)

### <font style="color:rgb(25, 27, 31);">位置 Embedding</font>
<font style="color:rgb(25, 27, 31);">位置 Embedding 用</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">PE</font>**<font style="color:rgb(25, 27, 31);">表示，</font>**<font style="color:rgb(25, 27, 31);">PE</font>**<font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">的维度与单词 Embedding 是一样的。PE 可以通过训练得到，也可以使用某种公式计算得到。在 Transformer 中采用了后者，计算公式如下：</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752147031381-e4ea5b93-322a-4e39-aa49-ca9d342b5bfa.png)

<font style="color:rgb(25, 27, 31);">其中，</font>**<font style="color:#DF2A3F;">pos 表示单词在句子中的位置</font>**<font style="color:rgb(25, 27, 31);">，</font>**<font style="color:#DF2A3F;">d 表示 PE的维度</font>**<font style="color:rgb(25, 27, 31);"> (与词 Embedding 一样)，2i 表示偶数的维度，2i+1 表示奇数维度 (即 2i≤d, 2i+1≤d)。使用这种公式计算 PE 有以下的好处：</font>

+ <font style="color:rgb(25, 27, 31);">使 PE 能够适应比训练集里面所有句子更长的句子，假设训练集里面最长的句子是有 20 个单词，突然来了一个长度为 21 的句子，则使用公式计算的方法可以计算出第 21 位的 Embedding。</font>
+ <font style="color:rgb(25, 27, 31);">可以让模型容易地计算出相对位置，对于固定长度的间距 k，</font>**<font style="color:rgb(25, 27, 31);">PE(pos+k)</font>**<font style="color:rgb(25, 27, 31);"> 可以用 </font>**<font style="color:rgb(25, 27, 31);">PE(pos)</font>**<font style="color:rgb(25, 27, 31);"> 计算得到。因为 Sin(A+B) = Sin(A)Cos(B) + Cos(A)Sin(B), Cos(A+B) = Cos(A)Cos(B) - Sin(A)Sin(B)。</font>

### Multi-Head Attention机制
将得到的X向量传递到 Multi-Head Attention中

#### Self-Attention机制
<font style="color:rgb(25, 27, 31);">在计算的时候需要用到矩阵</font>**<font style="color:rgb(25, 27, 31);">Q(查询),K(键值),V(值)</font>**<font style="color:rgb(25, 27, 31);">。</font>

<font style="color:rgb(25, 27, 31);">在实际中，</font>**<font style="color:#DF2A3F;">Self-Attention 接收的是输入(单词的表示向量x组成的矩阵X)</font>**<font style="color:rgb(25, 27, 31);"> 或者</font>**<font style="color:#DF2A3F;">上一个 Encoder block 的输出</font>**<font style="color:rgb(25, 27, 31);">。而</font>**<font style="color:rgb(25, 27, 31);">Q,K,V</font>**<font style="color:rgb(25, 27, 31);">正是通过 Self-Attention 的输入进行线性变换得到的。</font>

<font style="color:rgb(25, 27, 31);">Self-Attention 的输入用矩阵X进行表示，则可以使用线性变阵矩阵</font>**<font style="color:rgb(25, 27, 31);">WQ,WK,WV</font>**<font style="color:rgb(25, 27, 31);">计算得到</font>**<font style="color:rgb(25, 27, 31);">Q,K,V</font>**<font style="color:rgb(25, 27, 31);">。计算如下图所示，</font>**<font style="color:rgb(25, 27, 31);">注意 X, Q, K, V 的每一行都表示一个单词。</font>**

<font style="color:rgb(25, 27, 31);">  
</font><font style="color:rgb(25, 27, 31);">得到矩阵 Q, K, V之后就可以计算出 Self-Attention 的输出了，计算的公试</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752147398936-4201b499-15d4-4483-9e38-874f19b6d844.png)

<font style="color:rgb(25, 27, 31);">公式中计算矩阵</font>**<font style="color:rgb(25, 27, 31);">Q</font>**<font style="color:rgb(25, 27, 31);">和</font>**<font style="color:rgb(25, 27, 31);">K</font>**<font style="color:rgb(25, 27, 31);">每一行向量的内积，为了防止内积过大，因此除以 </font><font style="color:rgb(25, 27, 31);">dk</font><font style="color:rgb(25, 27, 31);"> 的平方根。</font>**<font style="color:rgb(25, 27, 31);">Q</font>**<font style="color:rgb(25, 27, 31);">乘以</font>**<font style="color:rgb(25, 27, 31);">K</font>**<font style="color:rgb(25, 27, 31);">的转置后，得到的矩阵行列数都为 n，n 为句子单词数，这个矩阵可以表示单词之间的 attention 强度。下图为</font>**<font style="color:rgb(25, 27, 31);">Q</font>**<font style="color:rgb(25, 27, 31);">乘以 </font><font style="color:rgb(25, 27, 31);">KT</font><font style="color:rgb(25, 27, 31);"> ，1234 表示的是句子中的单词。</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752200449956-6fd9471a-80e4-4afb-99c7-34a6a39fa969.png)

<font style="color:rgb(25, 27, 31);">得到</font><font style="color:rgb(25, 27, 31);">Q</font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">之后，使用 Softmax 计算每一个单词对于其他单词的 attention 系数，公式中的 Softmax 是对矩阵的每一行进行 Softmax，即每一行的和都变为 1.</font>

<font style="color:rgb(25, 27, 31);">上图中 Softmax 矩阵的第 1 行表示单词 1 与其他所有单词的 attention 系数，最终单词 1 的输出 </font><font style="color:rgb(25, 27, 31);">Z1</font><font style="color:rgb(25, 27, 31);"> 等于所有单词 i 的值 </font><font style="color:rgb(25, 27, 31);">Vi</font><font style="color:rgb(25, 27, 31);"> 根据 attention 系数的比例加在一起得到，如下图所示：</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752200670314-779c8bd5-8a89-4d01-a5cf-fab5b13ce360.png)

#### Mutil-self Attention机制
![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752200735574-732c6d8e-b412-4f15-80c4-f0528bca4eae.png)

<font style="color:rgb(25, 27, 31);">得到 8 个输出矩阵 </font><font style="color:rgb(25, 27, 31);">Z1</font><font style="color:rgb(25, 27, 31);"> 到 </font><font style="color:rgb(25, 27, 31);">Z8</font><font style="color:rgb(25, 27, 31);"> 之后，Multi-Head Attention 将它们拼接在一起 </font>**<font style="color:rgb(25, 27, 31);">(Concat)</font>**<font style="color:rgb(25, 27, 31);">，然后传入一个</font>**<font style="color:rgb(25, 27, 31);">Linear</font>**<font style="color:rgb(25, 27, 31);">层，得到 Multi-Head Attention 最终的输出</font>**<font style="color:rgb(25, 27, 31);">Z</font>**<font style="color:rgb(25, 27, 31);">。</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752200778926-a01fed59-0511-4a74-85e5-073487eae27d.png)

### ADD
<font style="color:rgb(25, 27, 31);">指 </font>**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);">+MultiHeadAttention(</font>**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);">)，是一种残差连接，通常用于解决多层网络训练的问题，可以让网络只关注当前差异的部分，在 ResNet 中经常用到：</font>

<font style="color:rgb(25, 27, 31);">表示将输入的X和通过Multi-self Attention输出的Z矩阵进行相加。</font>

### <font style="color:rgb(25, 27, 31);">Norm</font>
**<font style="color:rgb(25, 27, 31);">Norm</font>**<font style="color:rgb(25, 27, 31);">指 Layer Normalization，通常用于 RNN 结构，Layer Normalization 会将每一层神经元的输入都转成均值方差都一样的，这样可以加快收敛</font>

### <font style="color:rgb(25, 27, 31);">Feed Forward</font>
<font style="color:rgb(25, 27, 31);">Feed Forward 层比较简单，是一个两层的全连接层，第一层的激活函数为 Relu，第二层不使用激活函数，对应的公式如下。</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201145746-473f29ee-1257-4926-836c-07d32191f9c5.png)

<font style="color:rgb(145, 150, 161);">Feed Forward</font>

**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);">是输入，Feed Forward 最终得到的输出矩阵的维度与</font>**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);">一致。</font>

<font style="color:rgb(25, 27, 31);">  
</font><font style="color:rgb(25, 27, 31);">通过上面描述的 Multi-Head Attention, Feed Forward, Add & Norm 就可以构造出一个 Encoder block，Encoder block 接收输入矩阵</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">X</font><font style="color:rgb(25, 27, 31);">(</font><font style="color:rgb(25, 27, 31);">n</font><font style="color:rgb(25, 27, 31);">×</font><font style="color:rgb(25, 27, 31);">d</font><font style="color:rgb(25, 27, 31);">)</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">，并输出一个矩阵</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">O</font><font style="color:rgb(25, 27, 31);">(</font><font style="color:rgb(25, 27, 31);">n</font><font style="color:rgb(25, 27, 31);">×</font><font style="color:rgb(25, 27, 31);">d</font><font style="color:rgb(25, 27, 31);">)</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">。通过多个 Encoder block 叠加就可以组成 Encoder。</font>

<font style="color:rgb(25, 27, 31);">第一个 Encoder block 的输入为句子单词的表示向量矩阵，后续 Encoder block 的输入是前一个 Encoder block 的输出，最后一个 Encoder block 输出的矩阵就是</font>**<font style="color:rgb(25, 27, 31);">编码信息矩阵 C</font>**<font style="color:rgb(25, 27, 31);">，这一矩阵后续会用到 Decoder 中。</font>

## Encoder结构
![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752200803898-be608d06-ed86-4e6b-872b-5e4f8716553e.png)

### <font style="color:rgb(25, 27, 31);">第一个 Multi-Head Attention</font>
<font style="color:rgb(25, 27, 31);">Decoder block 的第一个 Multi-Head Attention 采用了 Masked 操作，因为在翻译的过程中是顺序翻译的，即翻译完第 i 个单词，才可以翻译第 i+1 个单词。通过 Masked 操作可以防止第 i 个单词知道 i+1 个单词之后的信息。下面以 "我有一只猫" 翻译成 "I have a cat" 为例，了解一下 Masked 操作。</font>

<font style="color:rgb(25, 27, 31);">下面的描述中使用了类似</font><font style="color:rgb(25, 27, 31);"> </font>[<font style="color:rgb(9, 64, 142);">Teacher Forcing</font>](https://zhida.zhihu.com/search?content_id=163422979&content_type=Article&match_order=1&q=Teacher+Forcing&zd_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ6aGlkYV9zZXJ2ZXIiLCJleHAiOjE3NTIzNzMxMzYsInEiOiJUZWFjaGVyIEZvcmNpbmciLCJ6aGlkYV9zb3VyY2UiOiJlbnRpdHkiLCJjb250ZW50X2lkIjoxNjM0MjI5NzksImNvbnRlbnRfdHlwZSI6IkFydGljbGUiLCJtYXRjaF9vcmRlciI6MSwiemRfdG9rZW4iOm51bGx9.xgH67bO3hm4P3OPOC1UknnJaA3cDL6Q4GOdjhM61R5Y&zhida_source=entity)<font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">的概念，不熟悉 Teacher Forcing 的童鞋可以参考以下上一篇文章Seq2Seq 模型详解。在 Decoder 的时候，是需要根据之前的翻译，求解当前最有可能的翻译，如下图所示。首先根据输入 "<Begin>" 预测出第一个单词为 "I"，然后根据输入 "<Begin> I" 预测下一个单词 "have"。</font>

<font style="color:rgb(25, 27, 31);">  
</font><font style="color:rgb(25, 27, 31);">Decoder 可以在训练的过程中使用 Teacher Forcing 并且并行化训练，即将正确的单词序列 (<Begin> I have a cat) 和对应输出 (I have a cat <end>) 传递到 Decoder。那么在预测第 i 个输出时，就要将第 i+1 之后的单词掩盖住，</font>**<font style="color:rgb(25, 27, 31);">注意 Mask 操作是在 Self-Attention 的 Softmax 之前使用的，下面用 0 1 2 3 4 5 分别表示 "<Begin> I have a cat <end>"。</font>**

**<font style="color:rgb(25, 27, 31);">第一步：</font>**<font style="color:rgb(25, 27, 31);">是 Decoder 的输入矩阵和</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Mask</font>****<font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">矩阵，输入矩阵包含 "<Begin> I have a cat" (0, 1, 2, 3, 4) 五个单词的表示向量，</font>**<font style="color:rgb(25, 27, 31);">Mask</font>****<font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">是一个 5×5 的矩阵。在</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Mask</font>****<font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">可以发现单词 0 只能使用单词 0 的信息，而单词 1 可以使用单词 0, 1 的信息，即只能使用之前的信息。</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201826146-2a7c345a-da48-40fe-ab70-a438ba42cdc5.png)

**<font style="color:rgb(25, 27, 31);">第二步：</font>**<font style="color:rgb(25, 27, 31);">接下来的操作和之前的 Self-Attention 一样，通过输入矩阵</font>**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);">计算得到</font>**<font style="color:rgb(25, 27, 31);">Q,K,V</font>**<font style="color:rgb(25, 27, 31);">矩阵。然后计算</font>**<font style="color:rgb(25, 27, 31);">Q</font>**<font style="color:rgb(25, 27, 31);">和</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">的乘积</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">Q</font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">。</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201852105-fd9edf77-cabe-48db-9615-6e76fae9a5c0.png)

<font style="color:rgb(145, 150, 161);">Q乘以K的转置</font>

**<font style="color:rgb(25, 27, 31);">第三步：</font>**<font style="color:rgb(25, 27, 31);">在得到</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">Q</font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">之后需要进行 Softmax，计算 attention score，我们在 Softmax 之前需要使用</font>**<font style="color:rgb(25, 27, 31);">Mask</font>**<font style="color:rgb(25, 27, 31);">矩阵遮挡住每一个单词之后的信息，遮挡操作如下：</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201852346-f54e189d-3141-4d91-856a-abfdc164e8df.png)

<font style="color:rgb(145, 150, 161);">Softmax 之前 Mask</font>

<font style="color:rgb(25, 27, 31);">得到</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Mask</font>**<font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">Q</font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">之后在</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Mask</font>****<font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Q</font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);">上进行 Softmax，每一行的和都为 1。但是单词 0 在单词 1, 2, 3, 4 上的 attention score 都为 0。</font>

**<font style="color:rgb(25, 27, 31);">第四步：</font>**<font style="color:rgb(25, 27, 31);">使用</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Mask</font>****<font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Q</font><font style="color:rgb(25, 27, 31);">K</font><font style="color:rgb(25, 27, 31);">T</font><font style="color:rgb(25, 27, 31);">与矩阵</font>**<font style="color:rgb(25, 27, 31);"> </font>****<font style="color:rgb(25, 27, 31);">V</font>**<font style="color:rgb(25, 27, 31);">相乘，得到输出</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Z</font>**<font style="color:rgb(25, 27, 31);">，则单词 1 的输出向量</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">Z</font><font style="color:rgb(25, 27, 31);">1</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">是只包含单词 1 信息的。</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201852700-d6493b73-534d-4ad6-8cb0-09733204f485.png)

<font style="color:rgb(145, 150, 161);">Mask 之后的输出</font>

**<font style="color:rgb(25, 27, 31);">第五步：</font>**<font style="color:rgb(25, 27, 31);">通过上述步骤就可以得到一个 Mask Self-Attention 的输出矩阵</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">Z</font><font style="color:rgb(25, 27, 31);">i</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">，然后和 Encoder 类似，通过 Multi-Head Attention 拼接多个输出</font><font style="color:rgb(25, 27, 31);">Z</font><font style="color:rgb(25, 27, 31);">i</font><font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">然后计算得到第一个 Multi-Head Attention 的输出</font>**<font style="color:rgb(25, 27, 31);">Z</font>**<font style="color:rgb(25, 27, 31);">，</font>**<font style="color:rgb(25, 27, 31);">Z</font>**<font style="color:rgb(25, 27, 31);">与输入</font>**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);">维度一样。</font>

### <font style="color:rgb(25, 27, 31);">第二个 Multi-Head Attention</font>
<font style="color:rgb(25, 27, 31);">Decoder block 第二个 Multi-Head Attention 变化不大， 主要的区别在于其中 Self-Attention 的</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">K, V</font>**<font style="color:rgb(25, 27, 31);">矩阵不是使用 上一个 Decoder block 的输出计算的，而是使用</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Encoder 的编码信息矩阵 C</font>****<font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">计算的。</font>

<font style="color:rgb(25, 27, 31);">根据 Encoder 的输出</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">C</font>**<font style="color:rgb(25, 27, 31);">计算得到</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">K, V</font>**<font style="color:rgb(25, 27, 31);">，根据上一个 Decoder block 的输出</font>**<font style="color:rgb(25, 27, 31);"> </font>****<font style="color:rgb(25, 27, 31);">Z</font>**<font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">计算</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Q</font>**<font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">(如果是第一个 Decoder block 则使用输入矩阵</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">X</font>**<font style="color:rgb(25, 27, 31);"> </font><font style="color:rgb(25, 27, 31);">进行计算)，后续的计算方法与之前描述的一致。</font>

<font style="color:rgb(25, 27, 31);">这样做的好处是在 Decoder 的时候，每一位单词都可以利用到 Encoder 所有单词的信息 (这些信息无需</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Mask</font>**<font style="color:rgb(25, 27, 31);">)。</font>

### <font style="color:rgb(25, 27, 31);">Softmax 预测输出单词</font>
<font style="color:rgb(25, 27, 31);">Decoder block 最后的部分是利用 Softmax 预测下一个单词，在之前的网络层我们可以得到一个最终的输出 Z，因为 Mask 的存在，使得单词 0 的输出 Z0 只包含单词 0 的信息，如下：</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201852083-836684d0-7f85-4554-92cc-7a81ffdcc108.png)

<font style="color:rgb(145, 150, 161);">Decoder Softmax 之前的 Z</font>

<font style="color:rgb(25, 27, 31);">Softmax 根据输出矩阵的每一行预测下一个单词：</font>

![](https://cdn.nlark.com/yuque/0/2025/png/34356568/1752201852138-08169ca6-43dd-4404-ba8b-07e11b9f8692.png)

<font style="color:rgb(145, 150, 161);">Decoder Softmax 预测</font>

<font style="color:rgb(25, 27, 31);">这就是 Decoder block 的定义，与 Encoder 一样，Decoder 是由多个 Decoder block 组合而成。</font>

  


# <font style="color:rgb(25, 27, 31);">Transformer 总结</font>
+ <font style="color:rgb(25, 27, 31);">Transformer 与 RNN 不同，可以比较好地并行训练。</font>
+ <font style="color:rgb(25, 27, 31);">Transformer 本身是不能利用单词的顺序信息的，因此需要在输入中添加位置 Embedding，否则 Transformer 就是一个词袋模型了。</font>
+ <font style="color:rgb(25, 27, 31);">Transformer 的重点是 Self-Attention 结构，其中用到的</font><font style="color:rgb(25, 27, 31);"> </font>**<font style="color:rgb(25, 27, 31);">Q, K, V</font>**<font style="color:rgb(25, 27, 31);">矩阵通过输出进行线性变换得到。</font>
+ <font style="color:rgb(25, 27, 31);">Transformer 中 Multi-Head Attention 中有多个 Self-Attention，可以捕获单词之间多种维度上的相关系数 attention score。</font>

  


